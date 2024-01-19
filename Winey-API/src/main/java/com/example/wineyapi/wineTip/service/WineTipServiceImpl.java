package com.example.wineyapi.wineTip.service;

import static com.example.wineyinfrastructure.amazonS3.enums.Folder.*;

import com.example.wineyapi.admin.wineTip.dto.WineTipReq;
import com.example.wineyapi.wineTip.convertor.WineTipConvertor;
import com.example.wineyapi.wineTip.dto.WineTipResponse;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.wineTip.entity.WineTip;
import com.example.wineydomain.wineTip.repository.WineTipRepository;
import com.example.wineyinfrastructure.amazonS3.enums.Folder;
import com.example.wineyinfrastructure.amazonS3.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WineTipServiceImpl implements WineTipService{
    private final WineTipRepository wineTipRepository;
    private final WineTipConvertor wineTipConvertor;
    private final S3UploadService s3UploadService;
    @Override
    public PageResponse<List<WineTipResponse.WineTipDto>> getWineTip(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<WineTip> wineTip = wineTipRepository.findByOrderByCreatedAtDesc(pageable);
        List<WineTipResponse.WineTipDto> wineTips = new ArrayList<>();

        wineTip.getContent().forEach(
                result -> wineTips.add(
                        wineTipConvertor.WineTipList(result)
                )
        );

        return new PageResponse<>(wineTip.isLast(), wineTip.getTotalElements(), wineTips);
    }

    @Override
    @Transactional
    public void uploadWineTip(WineTipReq.WineTipDto wineTipDto) {
        WineTip wineTip = wineTipRepository.save(wineTipConvertor.WineTip(wineTipDto));
        String imgUrl = s3UploadService.uploadImage(wineTip.getId(), WINE_TIP, wineTipDto.getMultipartFile());
        wineTip.setThumbnail(imgUrl);
        wineTipRepository.save(wineTip);
    }
}
