package com.example.wineyapi.user.service;

import com.example.wineyapi.user.converter.UserConverter;
import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineycommon.exception.MessageException;
import com.example.wineycommon.exception.errorcode.CommonResponseStatus;
import com.example.wineycommon.properties.CoolSmsProperties;
import com.example.wineycommon.properties.KakaoProperties;
import com.example.wineydomain.common.model.VerifyMessageStatus;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.repository.UserRepository;
import com.example.wineydomain.verificationMessage.entity.VerificationMessage;
import com.example.wineydomain.verificationMessage.repository.VerificationMessageRepository;
import com.example.wineyinfrastructure.oauth.kakao.client.KakaoFeignClient;
import com.example.wineyinfrastructure.oauth.kakao.client.KakaoLoginFeignClient;
import com.example.wineyinfrastructure.oauth.kakao.dto.KakaoUserInfoDto;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KakaoFeignClient kakaoFeignClient;
    private final KakaoLoginFeignClient kakaoLoginFeignClient;
    private final UserRepository userRepository;
    private final KakaoProperties kakaoProperties;
    private final CoolSmsProperties coolSmsProperties;
    private final VerificationMessageRepository verificationMessageRepository;
    private DefaultMessageService coolSmsService;

    @PostConstruct
    public void init() {
        coolSmsService = NurigoApp.INSTANCE.initialize(
                coolSmsProperties.getApiKey(),
                coolSmsProperties.getApiSecret(),
                coolSmsProperties.getDomain());
    }

    @Override
    public User login(SocialType socialType, UserRequest.LoginUserDTO request) {
        User user = null;
        switch (socialType) {
            case KAKAO :
                user = loginWithKakao(request);
                break;
            case GOOGLE :
                user = loginWithGoogle(request);
                break;
            case APPLE :
                user = loginWithApple(request);
                break;
        }
        return user;
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

    private User loginWithKakao(UserRequest.LoginUserDTO request) {
        String accessToken = request.getAccessToken();
        KakaoUserInfoDto kakaoUserInfoDto = kakaoFeignClient.getInfo(accessToken);
        return userRepository.findBySocialIdAndSocialType(kakaoUserInfoDto.getId(), SocialType.KAKAO)
                .orElseGet(() -> createUser(kakaoUserInfoDto));
    }

    private User createUser(KakaoUserInfoDto kakaoUserInfoDto) {
        User user = UserConverter.toUser(kakaoUserInfoDto);
        return userRepository.save(user);
    }

    private User loginWithGoogle(UserRequest.LoginUserDTO request) {
        return null;
    }

    private User loginWithApple(UserRequest.LoginUserDTO request) {
        return null;
    }

    @Override
    public Long delete(Long id) {
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
        try {
            // 1. 4자리 인증 번호 생성
            String verificationNumber = numberGen(4);

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
            throw new MessageException(CommonResponseStatus.VERIFICATION_DID_NOT_MATCH);
        }

        return verificationMessageRepository.save(verificationMessage);
    }
}
