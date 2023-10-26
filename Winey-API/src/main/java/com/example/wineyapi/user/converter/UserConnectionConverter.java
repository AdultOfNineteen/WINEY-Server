package com.example.wineyapi.user.converter;

import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.entity.UserConnection;
import org.springframework.stereotype.Component;

@Component
public class UserConnectionConverter {
    public UserConnection convertToUserConnection(User user) {
        return UserConnection.builder().user(user).cnt(1).build();
    }
}
