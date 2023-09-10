package com.example.wineyinfrastructure.amazonS3.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3UploadService {
    String uploadWineTipFile(Long id, MultipartFile multipartFile);
}
