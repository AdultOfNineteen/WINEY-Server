package com.example.wineyapi.tastingNote.service;

import com.example.wineyapi.tastingNote.convertor.TastingNoteConvertor;
import com.example.wineyapi.tastingNote.dto.TastingNoteRequest;
import com.example.wineyapi.tastingNote.dto.TastingNoteResponse;
import com.example.wineycommon.exception.BadRequestException;
import com.example.wineydomain.image.repository.TastingNoteImageRepository;
import com.example.wineydomain.tastingNote.entity.SmellKeyword;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.tastingNote.repository.SmellKeywordTastingNoteRepository;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineydomain.wine.repository.WineRepository;
import com.example.wineyinfrastructure.amazonS3.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.wineydomain.tastingNote.exception.UploadTastingNoteErrorCode.NOT_FOUNT_WINE;

@Service
@RequiredArgsConstructor
public class TastingNoteServiceImpl implements TastingNoteService{
    private final TastingNoteRepository tastingNoteRepository;
    private final TastingNoteConvertor tastingNoteConvertor;
    private final SmellKeywordTastingNoteRepository smellKeywordTastingNoteRepository;
    private final S3UploadService s3UploadService;
    private final TastingNoteImageRepository tastingNoteImageRepository;
    private final WineRepository wineRepository;
    @Override
    @Transactional
    public TastingNoteResponse.CreateTastingNoteDTO createTastingNote(User user, TastingNoteRequest.CreateTastingNoteDTO request) {
        Wine wine  = wineRepository.findById(request.getWineId()).orElseThrow(() ->  new BadRequestException(NOT_FOUNT_WINE));
        TastingNote tastingNote = tastingNoteRepository.save(tastingNoteConvertor.CreateTastingNote(request, user));

        for(SmellKeyword smellKeyword : request.getSmellKeywordList()){
            smellKeywordTastingNoteRepository.save(tastingNoteConvertor.SmellKeyword(smellKeyword, tastingNote));
        }

        List<String> imgList = s3UploadService.listUploadTastingNote(tastingNote.getId(),request.getMultipartFiles());

        for(String imgUrl : imgList){
            tastingNoteImageRepository.save(tastingNoteConvertor.TastingImg(tastingNote, imgUrl));
        }

        return new TastingNoteResponse.CreateTastingNoteDTO(tastingNote.getId());
    }
}
