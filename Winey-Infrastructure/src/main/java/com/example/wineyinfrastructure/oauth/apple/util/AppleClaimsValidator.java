package com.example.wineyinfrastructure.oauth.apple.util;

import io.jsonwebtoken.Claims;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class AppleClaimsValidator {

    private static final String NONCE_KEY = "nonce";

    // TODO - 환경변수 추가하기
    private final String iss;
    private final String clientId;
    private final String nonce;

    public AppleClaimsValidator(
            @Value("${oauth.apple.iss}") String iss,
            @Value("${oauth.apple.client-id}") String clientId,
            @Value("${oauth.apple.nonce}") String nonce
    ) {
        this.iss = iss;
        this.clientId = clientId;
        this.nonce = EncryptUtils.encrypt(nonce);
    }

    public boolean isValid(Claims claims) {
        return claims.getIssuer().contains(iss) &&
                claims.getAudience().equals(clientId) &&
                claims.get(NONCE_KEY, String.class).equals(nonce);
    }
}
