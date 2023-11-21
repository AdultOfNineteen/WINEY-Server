package com.example.wineyapi.wineGrade.service;

import com.example.wineyapi.wineGrade.dto.WineGradeResponse;

public interface WineGradeService {
    WineGradeResponse.GetWineGradeDTO getWineGrade(Long userId);
}
