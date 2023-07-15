package com.example.wineyapi.user.controller;

import com.example.wineyapi.user.dto.UserResponse;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.user.entity.SocialType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/login/{socialType}")
    public CommonResponse<UserResponse.LoginUser> login(@PathVariable SocialType socialType) {
        return null;
    }

    @DeleteMapping("/users/{userId}")
    public CommonResponse<UserResponse.DeleteUser> deleteUser(@PathVariable Long userId) {
        return null;
    }
}
