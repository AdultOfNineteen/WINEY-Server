package com.example.wineyapi.wineGrade.controller;

import com.example.wineyapi.wineGrade.service.WineGradeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "07-WineGrade⭐️",description = "와인등급 관련 API")
@RestController
@RequiredArgsConstructor
public class WineGradeController {
    private WineGradeService wineGradeService;

}
