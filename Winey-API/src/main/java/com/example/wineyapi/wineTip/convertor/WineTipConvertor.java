package com.example.wineyapi.wineTip.convertor;

import com.example.wineyapi.wineTip.dto.WineTipResponse;
import com.example.wineydomain.wineTip.entity.WineTip;
import org.springframework.stereotype.Component;

@Component
public class WineTipConvertor {
    public WineTipResponse.WineTipDto WineTipList(WineTip result) {
        return WineTipResponse.WineTipDto.builder()
                .wineTipId(result.getId())
                .thumbNail(result.getThumbnail())
                .title(result.getTitle())
                .build();
    }
}
