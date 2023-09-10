package com.example.wineyapi.wineTip.convertor;

import com.example.wineyapi.admin.wineTip.dto.WineTipReq;
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
                .url(result.getBlogUrl())
                .build();
    }

    public WineTip WineTip(WineTipReq.WineTipDto wineTipDto) {
        return WineTip
                .builder()
                .blogUrl(wineTipDto.getBlogUrl())
                .title(wineTipDto.getTitle())
                .build();
    }
}
