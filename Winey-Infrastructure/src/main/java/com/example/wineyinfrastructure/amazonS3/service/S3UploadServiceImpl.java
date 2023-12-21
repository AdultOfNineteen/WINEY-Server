package com.example.wineyinfrastructure.amazonS3.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wineycommon.exception.BadRequestException;
import com.example.wineycommon.exception.ForbiddenException;
import com.example.wineycommon.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.wineyinfrastructure.amazonS3.exception.FileDeleteException.IMAGE_DELETE_ERROR;
import static com.example.wineyinfrastructure.amazonS3.exception.FileUploadException.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3UploadServiceImpl implements S3UploadService{

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.base-url}")
    private String baseUrl;




    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {
            throw new BadRequestException(FILE_UPLOAD_NOT_EMPTY);
        }
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new BadRequestException(FILE_UPLOAD_EXCEPTION);
        }
        System.out.println(idxFileName);
        return fileName.substring(fileName.lastIndexOf("."));
    }


    private String getForUserFileName(Long userId, String fileExtension) {
        return "user/"
                + userId.toString()
                + "/"
                + UUID.randomUUID()
                + fileExtension;
    }

    private String getWineTipFileName(Long projectId, String fileExtension) {
        return "wine-tip/"
                + projectId.toString()
                + "/"
                + UUID.randomUUID()
                + fileExtension;
    }

    private String getTastingNoteFileName(Long projectId, String fileExtension) {
        return "tasting-note/"
                + projectId.toString()
                + "/"
                + UUID.randomUUID()
                + fileExtension;
    }
    public List<String> listUploadTastingNote(Long tastingNoteId, List<MultipartFile> multipartFiles){
        List<String> imgUrlList = new ArrayList<>();

        if(multipartFiles != null) {
            for (MultipartFile file : multipartFiles) {
                String fileName = getTastingNoteFileName(tastingNoteId, getFileExtension(file.getOriginalFilename()));
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(file.getSize());
                objectMetadata.setContentType(file.getContentType());

                try (InputStream inputStream = file.getInputStream()) {
                    amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
                    imgUrlList.add(amazonS3.getUrl(bucket, fileName).toString());
                } catch (IOException e) {
                    log.info("파일 업로드 실패 : " + tastingNoteId);
                    throw new ForbiddenException(IMAGE_UPLOAD_ERROR);
                }
            }

            return imgUrlList;
        }
        return null;
    }



    private String changeJpgToJpeg(String fileExtension) {
        if (fileExtension.equals("jpg")) {
            return "jpeg";
        }
        return fileExtension;
    }

    public String uploadWineTipFile(Long wineTipId,MultipartFile presentFile) {
        String fileName = getWineTipFileName(wineTipId, getFileExtension(presentFile.getOriginalFilename()));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(presentFile.getSize());
        objectMetadata.setContentType(presentFile.getContentType());

        try (InputStream inputStream = presentFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            log.info("파일 업로드 실패 와인 TIP ID : " + wineTipId);
            throw new ForbiddenException(IMAGE_UPLOAD_ERROR);
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public void deleteFile(String fileName){
        int index=fileName.indexOf(baseUrl);
        String fileRoute=fileName.substring(index+baseUrl.length()+1);
        System.out.println("deletefilename : "+fileRoute);
        try {
            boolean isObjectExist = amazonS3.doesObjectExist(bucket, fileRoute);
            if (isObjectExist) {
                amazonS3.deleteObject(bucket,fileRoute);
            } else {
                throw new InternalServerException(IMAGE_DELETE_ERROR);
            }
        } catch (Exception e) {
            throw new InternalServerException(IMAGE_DELETE_ERROR);
        }

    }
}
