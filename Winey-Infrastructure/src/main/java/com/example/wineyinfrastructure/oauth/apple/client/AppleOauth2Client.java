package com.example.wineyinfrastructure.oauth.apple.client;

import com.example.wineyinfrastructure.oauth.apple.dto.ApplePublicKeys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleOauth2Client", url = "https://appleid.apple.com/auth")
public interface AppleOauth2Client {
    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKeys();
}
