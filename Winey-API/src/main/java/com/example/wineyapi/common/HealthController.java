package com.example.wineyapi.common;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health API", description = "헬스 체크")
@RestController
public class HealthController {
    @GetMapping("/health")
    public String healthCheck() {
        return "I'm healthy";
    }
}
