package com.example.wineyapi.wineBadge.controller;

import com.example.wineyapi.common.annotation.CheckIdExistence;
import com.example.wineyapi.common.annotation.CheckOwnAccount;
import com.example.wineyapi.wineBadge.convertor.WineBadgeConvertor;
import com.example.wineyapi.wineBadge.dto.WineBadgeResponse;
import com.example.wineyapi.wineBadge.service.WineBadgeService;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.badge.dto.WineBadgeUserDTO;
import com.example.wineydomain.badge.entity.UserWineBadge;
import com.example.wineydomain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "06-WineBadge🪙",description = "와인뱃지 관련 API")
@RestController
@RequiredArgsConstructor
public class WineBadgeController {

    private final WineBadgeService wineBadgeService;

    @Operation(summary = "06-01 WineBadge🪙 마이페이지 WINEY 배지 목록 조회 Made By Peter", description = "본인의 와이니 (소믈리에 | 활동)뱃지 목록을 조회하는 API입니다.")
    @GetMapping("/users/{userId}/wine-badges")
    @CheckIdExistence @CheckOwnAccount
    public CommonResponse<WineBadgeResponse.WineBadgeListDTO> getWineBadgeList(@PathVariable Long userId,
                                                                           @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        List<WineBadgeUserDTO> userWineBadgeList = wineBadgeService.getWineBadgeListByUser(userId);
        return CommonResponse.onSuccess(WineBadgeConvertor.toWineBadgeListDTO(userWineBadgeList));
    }

    @Operation(summary = "06-02 WineBadge🪙 마이페이지 WINEY 배지 상세 조회 Made By Peter", description = "와이니 뱃지를 상세 조회하는 API입니다.")
    @GetMapping("/users/{userId}/wine-badges/{wineBadgeId}")
    @CheckIdExistence @CheckOwnAccount
    public CommonResponse<WineBadgeResponse.BadgeDTO> getWineBadge(@PathVariable Long userId,
                                                                   @PathVariable Long wineBadgeId,
                                                                   @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        WineBadgeUserDTO userWineBadge = wineBadgeService.getWineBadgeById(userId, wineBadgeId);
        return CommonResponse.onSuccess(WineBadgeConvertor.toBadgeDTO(userWineBadge));
    }

}
