package com.example.wineyapi.wineBadge.service;

import com.example.wineyapi.wineBadge.convertor.WineBadgeConvertor;
import com.example.wineycommon.annotation.RedissonLock;
import com.example.wineydomain.badge.entity.Badge;
import com.example.wineydomain.badge.entity.UserWineBadge;
import com.example.wineydomain.badge.repository.UserWineBadgeRepository;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.repository.UserRepository;
import com.example.wineydomain.wine.entity.Wine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.wineydomain.badge.entity.Badge.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WineBadgeServiceImpl implements WineBadgeService {
    private final UserWineBadgeRepository userWineBadgeRepository;
    private final TastingNoteRepository tastingNoteRepository;
    private final WineBadgeConvertor wineBadgeConvertor;
    private final UserRepository userRepository;

    @RedissonLock(LockName =  "뱃지-계산", key = "#userId")
    @Async("badge")
    public void calculateBadge(User user, Long userId) {
        List<TastingNote> tastingNotes = tastingNoteRepository.findByUser(user);

        List<Badge> badges = userWineBadgeRepository.findByUser(user).stream()
                .map(UserWineBadge::getBadge)
                .collect(Collectors.toList());

        log.info("Badge Lists : " + badges);

        List<UserWineBadge> userWineBadges = new ArrayList<>();

        userWineBadges.addAll(checkSommelierBadge(badges, tastingNotes, user));

        userWineBadges.addAll(calculateWineBadgeAboutTastingNote(badges, tastingNotes, user));

        userWineBadgeRepository.saveAll(userWineBadges);
        // 사용자 fcm 알림 + 알림 저장 로직 추가 구현해야함
    }

    @Override
    @Async("badge_provide_first_analysis")
    public void provideFirstAnalysis(User user) {
        user.setTasteNoteAnalysis(true);
        userWineBadgeRepository.save(wineBadgeConvertor.WineBadge(TASTE_DISCOVERY, user));
        userRepository.save(user);

        /*
        TODO 알림 보내기
         */

    }

    public List<UserWineBadge> checkSommelierBadge(List<Badge> badges, List<TastingNote> tastingNotes, User user) {
        List<UserWineBadge> userWineBadges = new ArrayList<>();
        int cnt = tastingNotes.size();

        if (cnt >= YOUNG_SOUMELIER.getRequiredActivity()) {
            if (!badges.contains(YOUNG_SOUMELIER))
                userWineBadges.add(wineBadgeConvertor.WineBadge(YOUNG_SOUMELIER, user));
        }
        if (cnt >= ENJOYMENT.getRequiredActivity()) {
            if (!badges.contains(ENJOYMENT)) userWineBadges.add(wineBadgeConvertor.WineBadge(ENJOYMENT, user));
        }
        if (cnt >= INTERMEDIATE_SOUMELIER.getRequiredActivity()) {
            if (!badges.contains(INTERMEDIATE_SOUMELIER))
                userWineBadges.add(wineBadgeConvertor.WineBadge(INTERMEDIATE_SOUMELIER, user));
        }
        if (cnt >= ADVANCED_SOUMELIER.getRequiredActivity()) {
            if (!badges.contains(ADVANCED_SOUMELIER))
                userWineBadges.add(wineBadgeConvertor.WineBadge(ADVANCED_SOUMELIER, user));
        }
        if (cnt >= MASTER_SOUMELIER.getRequiredActivity()) {
            if (!badges.contains(MASTER_SOUMELIER))
                userWineBadges.add(wineBadgeConvertor.WineBadge(MASTER_SOUMELIER, user));
        }
        return userWineBadges;
    }

    public List<UserWineBadge> calculateWineBadgeAboutTastingNote(List<Badge> badges, List<TastingNote> tastingNotes, User user) {
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

        return checkWineBadgeAboutTastingNote(badges, user, totalSmellKeywordCnt, roesTastingNotes, portTastingNotes, otherWineTastingNotes, maxWineCount, maxVarietalCount);
    }

    private List<UserWineBadge> checkWineBadgeAboutTastingNote(List<Badge> badges, User user, int totalSmellKeywordCnt, int roesTastingNotes, int portTastingNotes, int otherWineTastingNotes, int maxWineCount, int maxVarietalCount) {
        List<UserWineBadge> userWineBadges = new ArrayList<>();
        if (totalSmellKeywordCnt >= SMELL_ENTHUSIAST.getRequiredActivity()) {
            if (!badges.contains(SMELL_ENTHUSIAST)) userWineBadges.add(wineBadgeConvertor.WineBadge(SMELL_ENTHUSIAST, user));
        }
        if (maxWineCount >= FAVORITE_WINE.getRequiredActivity()) {
            if (!badges.contains(FAVORITE_WINE)) userWineBadges.add(wineBadgeConvertor.WineBadge(FAVORITE_WINE, user));
        }
        if (maxVarietalCount >= GRAPE_LOVER.getRequiredActivity()) {
            if (!badges.contains(GRAPE_LOVER)) userWineBadges.add(wineBadgeConvertor.WineBadge(GRAPE_LOVER, user));
        }
        if (roesTastingNotes >= 1 && portTastingNotes >= 1 && otherWineTastingNotes >= 1) {
            if (!badges.contains(NON_ALCOHOLIC)) userWineBadges.add(wineBadgeConvertor.WineBadge(NON_ALCOHOLIC, user));
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
}
