package com.example.wineyinfrastructure.firebase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDto {
    private String title;

    private String body;

    private String token;
}
