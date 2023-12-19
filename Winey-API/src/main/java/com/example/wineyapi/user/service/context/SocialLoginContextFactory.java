package com.example.wineyapi.user.service.context;

import com.example.wineyapi.user.service.strategy.AppleSocialLoginStrategy;
import com.example.wineyapi.user.service.strategy.GoogleSocialLoginStrategy;
import com.example.wineyapi.user.service.strategy.KakaoSocialLoginStrategy;
import com.example.wineyapi.user.service.strategy.SocialLoginStrategy;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.repository.UserRepository;
import com.example.wineyinfrastructure.oauth.apple.util.AppleOAuthUserProvider;
import com.example.wineyinfrastructure.oauth.google.client.GoogleOauth2Client;
import com.example.wineyinfrastructure.oauth.kakao.client.KakaoFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class SocialLoginContextFactory {
    private final UserRepository userRepository;
    private final KakaoFeignClient kakaoFeignClient;
    private final GoogleOauth2Client googleOauth2Client;
    private final AppleOAuthUserProvider appleOAuthUserProvider;

    private static SocialLoginContext kakaoSocialLoginContext;
    private static SocialLoginContext googleSocialLoginContext;
    private static SocialLoginContext appleSocialLoginContext;

    @PostConstruct
    private void init() {
        kakaoSocialLoginContext = createContext(new KakaoSocialLoginStrategy(userRepository, kakaoFeignClient));
        googleSocialLoginContext = createContext(new GoogleSocialLoginStrategy(userRepository, googleOauth2Client));
        appleSocialLoginContext = createContext(new AppleSocialLoginStrategy(userRepository, appleOAuthUserProvider));
    }

    private static SocialLoginContext createContext(SocialLoginStrategy strategy) {
        return new SocialLoginContext(strategy);
    }

    public static SocialLoginContext getContextBySocialType(SocialType socialType) {
        switch (socialType) {
            case KAKAO:
                return kakaoSocialLoginContext;
            case GOOGLE:
                return googleSocialLoginContext;
            case APPLE:
                return appleSocialLoginContext;
            default:
                throw new IllegalArgumentException("Invalid social type");
        }
    }
}
