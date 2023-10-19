package com.example.wineyinfrastructure.oauth.apple.util;

import com.example.wineycommon.exception.UserException;
import com.example.wineycommon.exception.errorcode.CommonResponseStatus;
import com.example.wineyinfrastructure.oauth.apple.client.AppleOauth2Client;
import com.example.wineyinfrastructure.oauth.apple.dto.AppleMember;
import com.example.wineyinfrastructure.oauth.apple.dto.ApplePublicKeys;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleOAuthUserProvider {

    private final AppleJwtParser appleJwtParser;
    private final AppleOauth2Client appleClient;
    private final PublicKeyGenerator publicKeyGenerator;
    private final AppleClaimsValidator appleClaimsValidator;

    public AppleMember getApplePlatformMember(String identityToken) {
        Map<String, String> headers = appleJwtParser.parseHeaders(identityToken);
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();

        PublicKey publicKey = publicKeyGenerator.generatePublicKey(headers, applePublicKeys);

        Claims claims = appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
        validateClaims(claims);
        return new AppleMember(claims.getSubject(), claims.get("email", String.class));
    }

    private void validateClaims(Claims claims) {
        if (!appleClaimsValidator.isValid(claims)) {
            throw new UserException(CommonResponseStatus.APPLE_INVALID_CLAIMS);
        }
    }
}
