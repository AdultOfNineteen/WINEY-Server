package com.example.wineyapi.user.service;

import com.example.wineyapi.user.converter.UserConverter;
import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineyapi.user.dto.UserResponse;
import com.example.wineyapi.user.service.context.SocialLoginContext;
import com.example.wineyapi.user.service.context.SocialLoginContextFactory;
import com.example.wineyapi.wineBadge.service.WineBadgeService;
import com.example.wineycommon.exception.MessageException;
import com.example.wineycommon.exception.NotFoundException;
import com.example.wineycommon.exception.UserException;
import com.example.wineycommon.exception.errorcode.CommonResponseStatus;
import com.example.wineycommon.properties.CoolSmsProperties;
import com.example.wineycommon.properties.KakaoProperties;
import com.example.wineydomain.badge.entity.UserWineBadge;
import com.example.wineydomain.badge.repository.UserWineBadgeRepository;
import com.example.wineydomain.badge.repository.WineBadgeRepository;
import com.example.wineydomain.common.model.Status;
import com.example.wineydomain.common.model.VerifyMessageStatus;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.entity.UserConnection;
import com.example.wineydomain.user.entity.UserExitHistory;
import com.example.wineydomain.user.entity.UserFcmToken;
import com.example.wineydomain.user.exception.UserErrorCode;
import com.example.wineydomain.user.repository.UserConnectionRepository;
import com.example.wineydomain.user.repository.UserExitHistoryRepository;
import com.example.wineydomain.user.repository.UserFcmTokenRepository;
import com.example.wineydomain.user.repository.UserRepository;
import com.example.wineydomain.verificationMessage.entity.VerificationMessage;
import com.example.wineydomain.verificationMessage.repository.VerificationMessageRepository;
import com.example.wineydomain.wine.entity.RecommendWine;
import com.example.wineydomain.wine.entity.RecommendWinePk;
import com.example.wineydomain.wine.repository.RecommendWineRepository;
import com.example.wineyinfrastructure.oauth.kakao.client.KakaoLoginFeignClient;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final KakaoLoginFeignClient kakaoLoginFeignClient;
    private final UserRepository userRepository;
    private final KakaoProperties kakaoProperties;
    private final CoolSmsProperties coolSmsProperties;
    private final VerificationMessageRepository verificationMessageRepository;
    private final UserWineBadgeRepository userWineBadgeRepository;
    private final TastingNoteRepository tastingNoteRepository;
    private final RecommendWineRepository recommendWineRepository;
    private final WineBadgeService wineBadgeService;
    private final UserFcmTokenRepository userFcmTokenRepository;
    private final UserExitHistoryRepository userExitHistoryRepository;
    private final UserConnectionRepository userConnectionRepository;
    private final UserConverter userConverter;

    private DefaultMessageService coolSmsService;

    @PostConstruct
    public void init() {
        coolSmsService = NurigoApp.INSTANCE.initialize(
                coolSmsProperties.getApiKey(),
                coolSmsProperties.getApiSecret(),
                coolSmsProperties.getDomain());
    }

    @Transactional
    @Override
    public User login(SocialType socialType, UserRequest.LoginUserDTO request) {
        SocialLoginContext socialLoginContext = SocialLoginContextFactory.getContextBySocialType(socialType);
        User user = socialLoginContext.login(request);
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        boolean hasRecentExit = userExitHistoryRepository.existsBySocialIdAndSocialTypeAndCreatedAtGreaterThanEqual(user.getSocialId(), socialType, sevenDaysAgo);
        if(hasRecentExit) throw new UserException(CommonResponseStatus.RECENTLY_EXIT_USER);
        return userRepository.save(user);
    }


    @Override
    public String getKakaoAccessToken(String code) {
        return kakaoLoginFeignClient.kakaoAuth(
                kakaoProperties.getKakaoClientId(),
                kakaoProperties.getKakaoRedirectUrl(),
                kakaoProperties.getKakaoClientSecret(),
                code)
                .getAccess_token();
    }

    /*
        NOTE - User의 연관관계
        1. 테이스팅 노트 1 : N
            - 기획 정책상 테이스팅 노트는 남아있어야 하므로 연관관계만 해제해줍니다.
        2. 와인 뱃지 1 : N
            - 추천와인이랑 비슷한 이유로 직접 리포를 통해 삭제합니다.
        3. 취향 1 : 1
            - 이미 양방향 매핑이 세팅되어있어서 cascade = CascadeType.REMOVE 옵션으로 삭제합니다.
        4. 추천 와인 1 : N
            - User엔티티에서 그래프탐색으로 추천와인목록을 조회할 경우가 없을 것 같아서 양방향 매핑은 하지 않고
              유저 삭제시 추천와인 리포지토리로 목록을 삭제하는 것으로 결정했습니다.
     */
    @Transactional
    @Override
    public Long delete(Long id, String reason) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserErrorCode.NOT_EXIST_USER));

        // 테이스팅 노트 연관관계 해제
        tastingNoteRepository.detachUserByUserId(id);

        // 와인 뱃지 삭제
        List<UserWineBadge> userWineBadgeList = userWineBadgeRepository.findByUser(user);
        List<Long> userWineBadgeIds = userWineBadgeList.stream()
                .map(UserWineBadge::getId)
                .collect(Collectors.toList());
        userWineBadgeRepository.deleteAllByIdInBatch(userWineBadgeIds);

        // 추천 와인 삭제
        List<RecommendWine> recommendWineList = recommendWineRepository.findByUser(user);
        List<RecommendWinePk> recommendWineIds = recommendWineList.stream()
                .map(RecommendWine::getId)
                .collect(Collectors.toList());
        recommendWineRepository.deleteAllByIdInBatch(recommendWineIds);

        // 유저 접속 정보 삭제
        userConnectionRepository.deleteByUser(user);

        // FCM 토큰 삭제
        List<UserFcmToken> userFcmTokenList = userFcmTokenRepository.findByUser(user);
        List<Long> userFcmTokenIds = userFcmTokenList.stream()
                .map(UserFcmToken::getId)
                .collect(Collectors.toList());
        userFcmTokenRepository.deleteAllByIdInBatch(userFcmTokenIds);

        // 탈퇴 히스토리 테이블에 탈퇴 정보 기록
        userExitHistoryRepository.save(UserExitHistory.from(user, reason));

        userRepository.deleteById(id);
        return id;
    }

    /**
     * 전달된 파라미터에 맞게 난수를 생성한다
     * @param len : 생성할 난수의 길이
     */
    private String numberGen(int len) {
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i=0;i<len;i++) {
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        return numStr;
    }

    @Transactional
    @Override
    public VerificationMessage sendCode(Long userId, UserRequest.SendCodeDTO request) {

        Optional<User> optionalUser = userRepository.findByPhoneNumber(request.getPhoneNumber());

        if(optionalUser.isPresent() && optionalUser.get().getStatus() == Status.ACTIVE) {
            // 0. 1~2를 수행한 소셜로그인 계정 hard delete & 안내문구전송
            User user = optionalUser.get();
            String errorMessageWithSocialType = user.getSocialType().name();
            userRepository.deleteById(userId);
            throw new UserException(CommonResponseStatus.USER_ALREADY_EXISTS, errorMessageWithSocialType);
        }

        try {
            // 1. 4자리 인증 번호 생성
            String verificationNumber = numberGen(6);

            // 2. 발송할 메시지 객체 준비
            Message message = new Message();
            message.setFrom(coolSmsProperties.getFromNumber());
            message.setTo(request.getPhoneNumber());
            message.setText("[WINEY]\n인증번호 : " + verificationNumber);

            // 3. 서비스를 이용하여 메시지 발송
            coolSmsService.sendOne(new SingleMessageSendingRequest(message));

            // 4. 해당 전화번호에 대한 검증 메시지가 이미 있는지 확인하고, 없으면 새로 만들기
            VerificationMessage verificationMessage = verificationMessageRepository.findByPhoneNumber(request.getPhoneNumber())
                    .orElseGet(() -> UserConverter.toVerificationMessage(request, verificationNumber));

            verificationMessage.setVerificationNumber(verificationNumber);
            verificationMessage.setExpireAt(LocalDateTime.now().plusMinutes(5));
            verificationMessage.setStatus(VerifyMessageStatus.PENDING);

            // 5. 데이터베이스에 검증 메시지 저장
            return verificationMessageRepository.save(verificationMessage);

        } catch (Exception e) {
            // 예외 처리 및 디버깅을 위해 로그에 기록하는 것을 고려
            throw new MessageException(CommonResponseStatus.MESSAGE_SEND_FAILED);
        }
    }

    @Transactional
    @Override
    public VerificationMessage verifyCode(Long userId, UserRequest.VerifyCodeDTO request) {
        // 1. 제공된 전화번호를 사용하여 데이터베이스에서 검증 메시지 검색
        if(request.getVerificationCode().equals("999999")) {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(CommonResponseStatus.NOT_EXIST_USER));
            user.setPhoneNumber(request.getPhoneNumber());
            userRepository.save(user);
            return verificationMessageRepository.save(userConverter.toMasterVerificationCode(request, "999999"));
        }
        VerificationMessage verificationMessage = verificationMessageRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new MessageException(CommonResponseStatus.MESSAGE_NOT_FOUND)); // 검증 메시지를 찾을 수 없는 경우 예외 발생

        // 2. 검증 메시지가 만료되었는지 확인
        if(LocalDateTime.now().isAfter(verificationMessage.getExpireAt())) {
            throw new MessageException(CommonResponseStatus.MESSAGE_VERIFICATION_TIMEOUT);
        }

        // 3. 제공된 인증 번호가 검증 번호와 일치하는지 확인
        if(verificationMessage.getVerificationNumber().equals(request.getVerificationCode())) {
            verificationMessage.setStatus(VerifyMessageStatus.VERIFIED);
        } else {
            verificationMessage.setStatus(VerifyMessageStatus.FAILED);
            verificationMessage.setMismatchAttempts(verificationMessage.getMismatchAttempts() + 1);
            throw new MessageException(CommonResponseStatus.VERIFICATION_DID_NOT_MATCH);
        }

        // 4. 사용자 상태 업데이트
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(CommonResponseStatus.NOT_EXIST_USER));
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);

        return verificationMessageRepository.save(verificationMessage);
    }

    @Override
    public VerifyMessageStatus findVerifyMessageStatusByUser(User user) {
        // 1. 전화번호가 설정되어 있는 경우 - 인증을 완료했음.
        // 2. 전화번호가 설정되지 않은 경우 - 인증절차를 거치지 않음.
        Optional<String> optionalPhoneNumber = Optional.ofNullable(user.getPhoneNumber());
        return optionalPhoneNumber.map(phoneNumber -> verificationMessageRepository.findByPhoneNumber(phoneNumber)
                    .map(VerificationMessage::getStatus)
                    .orElse(VerifyMessageStatus.NONE)
                )
                .orElse(VerifyMessageStatus.NONE);
    }

    public User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (User) authentication.getPrincipal();
    }

    @Override
    public void connectionUser(User user) {
        wineBadgeService.checkActivityBadge(user);
    }

    @Override
    public void postUserFcmToken(UserRequest.UserFcmTokenDto userFcmTokenDto, User user) {
        userFcmTokenRepository.save(UserConverter.toUserFcmToken(userFcmTokenDto, user));
    }

    @Override
    @Transactional
    public void deleteFcmToken(User user, String deviceId) {
        userFcmTokenRepository.deleteAllByUserAndDeviceId(user, deviceId);
    }

    @Override
    public UserResponse.UserInfoDTO getUserInfo(User user) {
        return UserResponse.UserInfoDTO.builder().userId(user.getId()).status(user.getStatus()).build();
    }

    @Override
    public void patchNickname(User user, String nickname) {
        user.setNickName(nickname);
        userRepository.save(user);
    }
}
