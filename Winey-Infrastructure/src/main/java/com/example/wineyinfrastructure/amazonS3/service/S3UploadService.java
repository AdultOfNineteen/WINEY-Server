package com.example.wineyinfrastructure.amazonS3.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3UploadService {
    String uploadWineTipFile(Long id, MultipartFile multipartFile);

    public List<String> listUploadTastingNote(Long tastingNoteId, List<MultipartFile> multipartFiles);

    void deleteFile(String url);
}
