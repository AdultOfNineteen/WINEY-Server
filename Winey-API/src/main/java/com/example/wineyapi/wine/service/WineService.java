package com.example.wineyapi.wine.service;


import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.redis.model.RecommendWineDTO;
import com.example.wineydomain.user.entity.User;

import java.util.List;

public interface WineService {
    List<RecommendWineDTO> recommendWine(User user);

    PageResponse<List<WineResponse.SearchWineDto>> searchWineList(Integer page, Integer size, String content);

    WineResponse.WineDTO getWineDTOById(Long wineId);
}
