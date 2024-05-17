package com.example.wineyapi.user.service.strategy;

import com.example.wineyapi.user.converter.UserConverter;
import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineydomain.common.model.Status;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.repository.UserRepository;
import com.example.wineyinfrastructure.oauth.google.client.GoogleOauth2Client;
import com.example.wineyinfrastructure.oauth.google.dto.GoogleUserInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoogleSocialLoginStrategy implements SocialLoginStrategy {

    private final UserRepository userRepository;
    private final GoogleOauth2Client googleOauth2Client;

    @Override
    public User login(UserRequest.LoginUserDTO request) {
        String identityToken = request.getAccessToken();
        GoogleUserInfo googleUserInfo = googleOauth2Client.verifyToken(identityToken);
        return userRepository.findBySocialIdAndSocialTypeAndStatus(googleUserInfo.getSub(), SocialType.GOOGLE, Status.ACTIVE)
                .orElseGet(() -> UserConverter.toUser(googleUserInfo));
    }
}
