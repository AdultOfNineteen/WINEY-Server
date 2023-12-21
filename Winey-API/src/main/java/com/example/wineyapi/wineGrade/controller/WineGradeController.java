package com.example.wineyapi.wineGrade.controller;

import com.example.wineyapi.common.annotation.CheckIdExistence;
import com.example.wineyapi.common.annotation.CheckOwnAccount;
import com.example.wineyapi.wineGrade.dto.WineGradeResponse;
import com.example.wineyapi.wineGrade.service.WineGradeService;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.common.WineGrade;
import com.example.wineydomain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "07-WineGrade⭐️",description = "와인등급 관련 API")
@RestController
@RequiredArgsConstructor
public class WineGradeController {
    private final WineGradeService wineGradeService;

    @Operation(summary = "07-01 WineGrade⭐ 마이페이지 와인 등급 조회 #FRAME Made By Peter", description = "본인의 현재 와인등급과 다음 달 예상 등급을 조회하는 API입니다.")
    @GetMapping("/users/{userId}/wine-grade")
    @CheckIdExistence @CheckOwnAccount
    public CommonResponse<WineGradeResponse.GetWineGradeDTO> getUserWineGrade(@PathVariable Long userId,
                                                                          @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        WineGradeResponse.GetWineGradeDTO getWineGradeDTO = wineGradeService.getWineGrade(userId);
        return CommonResponse.onSuccess(getWineGradeDTO);
    }

    @Operation(summary = "07-02 WineGrade⭐ 와인 등급 기준 조회 #FRAME Made By Peter", description = "와인 등급 기준을 조회하는 API입니다.")
    @GetMapping("/users/wine-grades")
    public CommonResponse<List<WineGradeResponse.WineGradeDTO>> getWineGrade() {
        List<WineGradeResponse.WineGradeDTO> wineGradeDTOList = Arrays.stream(WineGrade.values())
                .map(wineGrade -> WineGradeResponse.WineGradeDTO.builder()
                            .name(wineGrade.name())
                            .minCount(wineGrade.getMinCount())
                            .maxCount(wineGrade.getMaxCount())
                            .build()
                )
                .collect(Collectors.toList());
        return CommonResponse.onSuccess(wineGradeDTOList);
    }
}
