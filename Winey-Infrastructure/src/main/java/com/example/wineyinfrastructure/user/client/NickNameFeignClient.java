package com.example.wineyinfrastructure.user.client;

import com.example.wineyinfrastructure.user.dto.NickNameRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "NickNameFeignClient",
        url = "https://nickname.hwanmoo.kr"
)
@Component
public interface NickNameFeignClient {

    @GetMapping(value = "/?format=json&count=1&max_length=8")
    NickNameRes getNickName();
}
