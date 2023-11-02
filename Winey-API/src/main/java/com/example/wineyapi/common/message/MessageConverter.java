package com.example.wineyapi.common.message;

import com.example.wineydomain.badge.entity.Badge;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.entity.UserFcmToken;
import com.example.wineyinfrastructure.firebase.dto.NotificationRequestDto;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter {

    public NotificationRequestDto toNotificationRequestDto(Badge badge, UserFcmToken fcmToken, User user) {
        return NotificationRequestDto.builder()
                .title("새로운 뱃지를 얻었어요~~~")
                .body(toMessageBodyWineBadge(user.getNickName(), badge.getBadgeName()))
                .token(fcmToken.getFcmToken())
                .build();
    }

    private String toMessageBodyWineBadge(String nickName, String badgeName) {
        return nickName + "님 " + badgeName + "을 얻었어요 확인하러 가볼까요";
    }
}
