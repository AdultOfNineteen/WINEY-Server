package com.example.wineyapi.wineTip.service;

import com.example.wineyapi.wineTip.dto.WineTipResponse;
import com.example.wineycommon.reponse.PageResponse;

import java.util.List;

public interface WineTipService {
    PageResponse<List<WineTipResponse.WineTipDto>> getWineTip(Integer page, Integer size);
}
