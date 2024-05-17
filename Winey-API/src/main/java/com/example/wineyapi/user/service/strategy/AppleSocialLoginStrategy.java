package com.example.wineyapi.user.service.strategy;

import com.example.wineyapi.user.converter.UserConverter;
import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineydomain.common.model.Status;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.repository.UserRepository;
import com.example.wineyinfrastructure.oauth.apple.dto.AppleMember;
import com.example.wineyinfrastructure.oauth.apple.util.AppleOAuthUserProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppleSocialLoginStrategy implements SocialLoginStrategy {

    private final UserRepository userRepository;
    private final AppleOAuthUserProvider appleOAuthUserProvider;

    @Override
    public User login(UserRequest.LoginUserDTO request) {
        String identityToken = request.getAccessToken();
        AppleMember appleMember = appleOAuthUserProvider.getApplePlatformMember(identityToken);
        return userRepository.findBySocialIdAndSocialTypeAndStatus(appleMember.getSocialId(), SocialType.APPLE, Status.ACTIVE)
                .orElseGet(() -> UserConverter.toUser(appleMember));
    }
}
