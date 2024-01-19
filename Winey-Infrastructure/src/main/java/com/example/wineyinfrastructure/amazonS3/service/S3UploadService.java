package com.example.wineyinfrastructure.amazonS3.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.example.wineyinfrastructure.amazonS3.enums.Folder;

public interface S3UploadService {
    List<String> imageListUpload(Long id, Folder folder, List<MultipartFile> multipartFiles);

    void deleteFile(String url);

	String uploadImage(Long id, Folder folder, MultipartFile multipartFile);
}
