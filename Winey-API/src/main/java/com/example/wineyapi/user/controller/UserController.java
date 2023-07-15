package com.example.wineyapi.user.controller;

import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineyapi.user.dto.UserResponse;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.user.entity.SocialType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @PostMapping("/login/{socialType}")
    public CommonResponse<UserResponse.LoginUserDTO> login(@PathVariable SocialType socialType,
                                                           @RequestBody UserRequest.LoginUserDTO request) {
        return null;
    }

    @DeleteMapping("/users/{userId}")
    public CommonResponse<UserResponse.DeleteUserDTO> deleteUser(@PathVariable Long userId) {
        return null;
    }
}
