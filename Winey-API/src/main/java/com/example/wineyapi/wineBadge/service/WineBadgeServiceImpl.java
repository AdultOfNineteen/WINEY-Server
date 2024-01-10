package com.example.wineyapi.wineBadge.service;

import com.example.wineyapi.common.message.MessageConverter;
import com.example.wineyapi.common.util.TimeUtils;
import com.example.wineyapi.notification.service.NotificationService;
import com.example.wineyapi.user.converter.UserConnectionConverter;
import com.example.wineyapi.wineBadge.convertor.WineBadgeConvertor;
import com.example.wineycommon.annotation.RedissonLock;
import com.example.wineycommon.exception.NotFoundException;
import com.example.wineydomain.badge.dto.WineBadgeUserDTO;
import com.example.wineydomain.badge.entity.UserWineBadge;
import com.example.wineydomain.badge.entity.WineBadge;
import com.example.wineydomain.badge.exception.WineBadgeErrorCode;
import com.example.wineydomain.badge.repository.UserWineBadgeRepository;
import com.example.wineydomain.badge.repository.WineBadgeRepository;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.entity.UserConnection;
import com.example.wineydomain.user.entity.UserFcmToken;
import com.example.wineydomain.user.repository.UserConnectionRepository;
import com.example.wineydomain.user.repository.UserRepository;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineyinfrastructure.firebase.dto.NotificationRequestDto;
import com.example.wineyinfrastructure.firebase.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.wineycommon.constants.WineyStatic.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WineBadgeServiceImpl implements WineBadgeService {
    private final UserWineBadgeRepository userWineBadgeRepository;
    private final WineBadgeRepository wineBadgeRepository;
    private final TastingNoteRepository tastingNoteRepository;
    private final WineBadgeConvertor wineBadgeConvertor;
    private final UserRepository userRepository;
    private final UserConnectionRepository userConnectionRepository;
    private final UserConnectionConverter userConnectionConverter;
    private final TimeUtils timeUtils;
    private final MessageService messageService;
    private final MessageConverter messageConverter;
    private final NotificationService notificationService;


    @RedissonLock(LockName =  "뱃지-계산", key = "#userId")
    @Async("badge")
    public void calculateBadge(User user, Long userId) {
        List<WineBadge> wineBadges = getWineBadgeAll();
        List<TastingNote> tastingNotes = tastingNoteRepository.findByUser(user);

        List<WineBadge> userWineBadgeLists = userWineBadgeRepository.findByUser(user).stream()
            .map(UserWineBadge::getWineBadge)
            .collect(Collectors.toList());

        List<UserWineBadge> userWineBadges = new ArrayList<>();

        userWineBadges.addAll(checkSommelierBadge(tastingNotes, user, userWineBadgeLists, wineBadges));

        userWineBadges.addAll(calculateWineBadgeAboutTastingNote(tastingNotes, user, userWineBadgeLists, wineBadges));

        userWineBadgeRepository.saveAll(userWineBadges);

        sendMessageGetWineBadges(userWineBadges, user);
    }

    @Cacheable(value = "wineBadgeAll", key = "'all'", cacheManager = "redisCacheManager")
    public List<WineBadge> getWineBadgeAll() {
        return wineBadgeRepository.findByOrderByIdAsc();
    }

    public void sendMessageGetWineBadges(List<UserWineBadge> userWineBadges, User user) {
        for(UserWineBadge wineBadge : userWineBadges) {
            sendMessageGetWineBadge(wineBadge, user);
        }
    }

    private WineBadge getWineBadge(int index, List<WineBadge> wineBadges){
        return wineBadges.get(index);
    }

    public void sendMessageGetWineBadge(UserWineBadge wineBadge, User user) {
        List<NotificationRequestDto> notificationRequestDtos = new ArrayList<>();
        for(UserFcmToken fcmToken : user.getUserFcmTokens()) {
            notificationRequestDtos.add(messageConverter.toNotificationRequestDto(wineBadge.getWineBadge().getName(), fcmToken, user));
        }
        if(!notificationRequestDtos.isEmpty()) {
            messageService.sendNotifications(notificationRequestDtos);
            notificationService.saveNotification(notificationRequestDtos, user);
        }
    }

    @Override
    @Async("badge_provide_first_analysis")
    public void provideFirstAnalysis(User user) {
        user.setTastingNoteAnalyzed(true);
        userRepository.save(user);

        UserWineBadge userWineBadge = userWineBadgeRepository.save(wineBadgeConvertor.convertWineBadge(wineBadgeRepository.findByBadge(TASTE_DISCOVERY), user));

        sendMessageGetWineBadge(userWineBadge, user);
    }

    @Override
    @Async("connect_user")
    @RedissonLock(LockName = "유저 연속 접속 확인", key = "#user.id")
    public void checkActivityBadge(User user) {
        UserConnection userConnection = user.getUserConnection();
        if(userConnection == null){
            userConnectionRepository.save(userConnectionConverter.convertToUserConnection(user));
        }else{
            checkContinueConnection(user, userConnection);
        }
    }

    private void checkContinueConnection(User user, UserConnection userConnection) {
        boolean isConnectedToday = timeUtils.checkNowDate(userConnection.getUpdatedAt());
        boolean isConnectedYesterday = timeUtils.checkOneDayBefore(userConnection.getUpdatedAt());

        if(isConnectedToday){
            return;  // 오늘 이미 접속한 기록이 있으므로 함수 종료
        }
        if(isConnectedYesterday){
            userConnection.setCnt(userConnection.getCnt() + 1);
            userConnectionRepository.save(userConnection);
            checkUserConnectionBadge(userConnection, user);
        } else {
            userConnectionRepository.deleteByUser(user);
        }
    }

    private void checkUserConnectionBadge(UserConnection userConnection, User user) {
        List<WineBadge> userWineBadgeLists = userWineBadgeRepository.findByUser(user).stream()
            .map(UserWineBadge::getWineBadge)
            .collect(Collectors.toList());

        WineBadge wineExcitementBadge = getWineBadge(WINE_EXCITEMENT_INDEX, userWineBadgeLists);
        if(userConnection.getCnt() == wineExcitementBadge.getRequiredActivity()){
            if(!userWineBadgeLists.contains(wineExcitementBadge)) userWineBadgeRepository.save(wineBadgeConvertor.convertWineBadge(wineExcitementBadge, user));
        }
        WineBadge wineAddictBadge = getWineBadge(WINE_ADDICT_INDEX, userWineBadgeLists);
        if(userConnection.getCnt() == wineExcitementBadge.getRequiredActivity()){
            if(!userWineBadgeLists.contains(wineAddictBadge)) userWineBadgeRepository.save(wineBadgeConvertor.convertWineBadge(wineAddictBadge, user));
        }
    }



    public List<UserWineBadge> checkSommelierBadge(List<TastingNote> tastingNotes, User user, List<WineBadge> userWineBadgeLists,
        List<WineBadge> wineBadges) {
        List<UserWineBadge> userWineBadges = new ArrayList<>();
        int cnt = tastingNotes.size();

        WineBadge youngSomelierBadge = getWineBadge(YOUNG_SOMMELIER_INDEX, wineBadges);
        if (cnt >= youngSomelierBadge.getRequiredActivity()) {
            if (!userWineBadgeLists.contains(youngSomelierBadge))
                userWineBadges.add(wineBadgeConvertor.convertWineBadge(youngSomelierBadge, user));
        }
        WineBadge enjoymentBadge = getWineBadge(ENJOYMENT_INDEX, wineBadges);
        if (cnt >= enjoymentBadge.getRequiredActivity()) {
            if (!userWineBadgeLists.contains(enjoymentBadge)) userWineBadges.add(wineBadgeConvertor.convertWineBadge(enjoymentBadge, user));
        }
        WineBadge intermediateSomelierBadge = getWineBadge(INTERMEDIATE_SOMMELIER_INDEX, wineBadges);
        if (cnt >= intermediateSomelierBadge.getRequiredActivity()) {
            if (!userWineBadgeLists.contains(intermediateSomelierBadge)) userWineBadges.add(wineBadgeConvertor.convertWineBadge(intermediateSomelierBadge, user));
        }
        WineBadge advancedSomelierBadge = getWineBadge(ADVANCED_SOMMELIER_INDEX, wineBadges);
        if (cnt >= advancedSomelierBadge.getRequiredActivity()) {
            if (!userWineBadgeLists.contains(advancedSomelierBadge)) userWineBadges.add(wineBadgeConvertor.convertWineBadge(advancedSomelierBadge, user));
        }
        WineBadge masterSomelierBadge = getWineBadge(MASTER_SOMMELIER_INDEX, wineBadges);
        if (cnt >= masterSomelierBadge.getRequiredActivity()) {
            if (!userWineBadgeLists.contains(masterSomelierBadge)) userWineBadges.add(wineBadgeConvertor.convertWineBadge(masterSomelierBadge, user));
        }
        return userWineBadges;
    }

    public List<UserWineBadge> calculateWineBadgeAboutTastingNote(List<TastingNote> tastingNotes, User user,
        List<WineBadge> userWineBadgeLists, List<WineBadge> wineBadges) {
        int totalSmellKeywordCnt = 0;
        int roesTastingNotes = 0;
        int portTastingNotes = 0;
        int otherWineTastingNotes = 0;
        Map<String, Integer> sameVarietalMap = new HashMap<>();
        Map<Long, Integer> sameWineMap = new HashMap<>();

        for (TastingNote tastingNote : tastingNotes) {
            Wine wine = tastingNote.getWine();

            if (tastingNote.getSmellKeywordTastingNote().size() >= 3) {
                totalSmellKeywordCnt += 1;
            }

            sameWineMap.put(wine.getId(), sameWineMap.getOrDefault(wine.getId(), 0) + 1);

            String[] varietalLists = wine.getVarietal().split(", ");

            for (String varietalList : varietalLists) {
                sameVarietalMap.put(varietalList, sameVarietalMap.getOrDefault(varietalList, 0) + 1);
            }

            switch (wine.getType()) {
                case ROSE:
                    roesTastingNotes += 1;
                    break;
                case FORTIFIED:
                    portTastingNotes += 1;
                    break;
                case OTHER:
                    otherWineTastingNotes += 1;
                    break;
                default:
                    break;
            }
        }

        int maxWineCount = calculateMaxWine(sameWineMap);
        int maxVarietalCount = calculateMaxVarietalCount(sameVarietalMap);

        return checkWineBadgeAboutTastingNote(user, totalSmellKeywordCnt, roesTastingNotes, portTastingNotes,
            otherWineTastingNotes, maxWineCount, maxVarietalCount, userWineBadgeLists, wineBadges);
    }

    private List<UserWineBadge> checkWineBadgeAboutTastingNote(User user, int totalSmellKeywordCnt, int roesTastingNotes, int portTastingNotes, int otherWineTastingNotes, int maxWineCount, int maxVarietalCount,
        List<WineBadge> userWineBadgeLists, List<WineBadge> wineBadges) {
        List<UserWineBadge> userWineBadges = new ArrayList<>();
        WineBadge smellEnthusiastBadge = getWineBadge(SMELL_ENTHUSIAST_INDEX, wineBadges);
        if (totalSmellKeywordCnt >= smellEnthusiastBadge.getRequiredActivity()) {
            if (!userWineBadgeLists.contains(smellEnthusiastBadge)) userWineBadges.add(wineBadgeConvertor.convertWineBadge(smellEnthusiastBadge, user));
        }
        WineBadge favoriteWineBadge = getWineBadge(FAVORITE_WINE_INDEX, wineBadges);
        if (maxWineCount >= favoriteWineBadge.getRequiredActivity()) {
            if (!userWineBadgeLists.contains(favoriteWineBadge)) userWineBadges.add(wineBadgeConvertor.convertWineBadge(favoriteWineBadge, user));
        }
        WineBadge grapeLoverBadge = getWineBadge(GRAPE_LOVER_INDEX, wineBadges);
        if (maxVarietalCount >= grapeLoverBadge.getRequiredActivity()) {
            if (!userWineBadgeLists.contains(grapeLoverBadge)) userWineBadges.add(wineBadgeConvertor.convertWineBadge(grapeLoverBadge, user));
        }
        WineBadge nonAlcoholicBadge = getWineBadge(NON_ALCOHOLIC_INDEX, wineBadges);
        if (roesTastingNotes >= 1 && portTastingNotes >= 1 && otherWineTastingNotes >= 1) {
            if (!userWineBadgeLists.contains(nonAlcoholicBadge)) userWineBadges.add(wineBadgeConvertor.convertWineBadge(nonAlcoholicBadge, user));
        }
        return userWineBadges;
    }

    private int calculateMaxVarietalCount(Map<String, Integer> sameVarietalMap) {
        if (sameVarietalMap.isEmpty()) return 0;
        else return Collections.max(sameVarietalMap.values());
    }

    private int calculateMaxWine(Map<Long, Integer> sameWineMap) {
        if (sameWineMap.isEmpty()) return 0;
        else return Collections.max(sameWineMap.values());
    }

    @Transactional(readOnly = true)
    @Override
    public List<WineBadgeUserDTO> getWineBadgeListByUser(Long userId) {
        return wineBadgeRepository.findWineBadgesWithUser(userId);
    }

    @Transactional
    @Override
    public WineBadgeUserDTO getWineBadgeById(Long userId, Long wineBadgeId) {
        Optional<UserWineBadge> optionalUserWineBadge = userWineBadgeRepository.findByUser_IdAndWineBadge_Id(userId, wineBadgeId);
        if(optionalUserWineBadge.isPresent()) {
            optionalUserWineBadge.get().setIsRead(Boolean.TRUE);
            return WineBadgeUserDTO.from(optionalUserWineBadge.get());
        }
        WineBadge wineBadge = wineBadgeRepository.findById(wineBadgeId)
                .orElseThrow(() -> new NotFoundException(WineBadgeErrorCode.BADGE_NOT_FOUND));
        return WineBadgeUserDTO.from(wineBadge);
    }
}
