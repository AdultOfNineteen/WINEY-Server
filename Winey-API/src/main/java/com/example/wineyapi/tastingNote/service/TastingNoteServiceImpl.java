package com.example.wineyapi.tastingNote.service;

import com.example.wineyapi.tastingNote.convertor.TastingNoteConvertor;
import com.example.wineyapi.tastingNote.dto.TastingNoteRequest;
import com.example.wineyapi.tastingNote.dto.TastingNoteResponse;
import com.example.wineyapi.wineBadge.service.WineBadgeService;
import com.example.wineycommon.exception.BadRequestException;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.image.entity.TastingNoteImage;
import com.example.wineydomain.image.repository.TastingNoteImageRepository;
import com.example.wineydomain.tastingNote.entity.SmellKeyword;
import com.example.wineydomain.tastingNote.entity.SmellKeywordTastingNote;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.tastingNote.repository.SmellKeywordTastingNoteRepository;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineydomain.wine.entity.WineType;
import com.example.wineydomain.wine.repository.WineRepository;
import com.example.wineyinfrastructure.amazonS3.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.wineydomain.tastingNote.exception.GetTastingNoteErrorCode.NOT_FOUND_TASTING_NOTE;
import static com.example.wineydomain.tastingNote.exception.UploadTastingNoteErrorCode.NOT_FOUNT_WINE;
import static com.example.wineyinfrastructure.amazonS3.enums.Folder.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TastingNoteServiceImpl implements TastingNoteService{
    private final TastingNoteRepository tastingNoteRepository;
    private final TastingNoteConvertor tastingNoteConvertor;
    private final SmellKeywordTastingNoteRepository smellKeywordTastingNoteRepository;
    private final S3UploadService s3UploadService;
    private final TastingNoteImageRepository tastingNoteImageRepository;
    private final WineRepository wineRepository;
    private final WineBadgeService wineBadgeService;

    @Override
    public TastingNoteResponse.TasteAnalysisDTO tasteAnalysis(User user) {
        List<TastingNote> tastingNotes = tastingNoteRepository.findByUserAndBuyAgainAndIsDeleted(user, true, false);

        if(tastingNotes.isEmpty()){
            if(!user.getIsTasteNoteAnalysed()) wineBadgeService.provideFirstAnalysis(user);
        }

        return tastingNoteConvertor.TasteAnalysis(tastingNotes);
    }

    @Override
    public TastingNoteResponse.CheckTastingNote checkTastingNote(User user) {
        boolean checkTastingNote = tastingNoteRepository.existsByUserAndBuyAgain(user, true);
        return new TastingNoteResponse.CheckTastingNote(checkTastingNote);
    }

    @Override
    public TastingNoteResponse.TastingNoteDTO getTastingNote(Long noteId) {
        TastingNote tastingNote = tastingNoteRepository.getTastingNote(noteId, false);
        if(tastingNote == null) throw new BadRequestException(NOT_FOUND_TASTING_NOTE);
        return tastingNoteConvertor.TastingNote(tastingNote);
    }

    @Override
    public TastingNoteResponse.NoteFilterDTO getNoteFilter(User user) {
        List<TastingNote> tastingNotes = tastingNoteRepository.findByUserAndIsDeleted(user, false);

        return tastingNoteConvertor.NoteFilter(tastingNotes);
    }

    
  @Override
    public void deleteTastingNote(User user, Long noteId) {
        TastingNote tastingNote = finByUserAndTastingNoteId(user, noteId);

        tastingNote.setIsDeleted(true);

        deleteImgFile(tastingNote.getTastingNoteImages());

        tastingNoteRepository.save(tastingNote);
    }

    private TastingNote finByUserAndTastingNoteId(User user, Long noteId) {
        return tastingNoteRepository.findByUserAndId(user, noteId).orElseThrow(() -> new BadRequestException(NOT_FOUND_TASTING_NOTE));
    }

    @Override
    @Transactional
    public void updateTastingNote(User user, TastingNoteRequest.UpdateTastingNoteDTO request,
        List<MultipartFile> multipartFiles, Long noteId) {
        TastingNote tastingNote = finByUserAndTastingNoteId(user, noteId);
        updateTastingNoteInfo(request, tastingNote);
        updateTastingNoteSmellKeyword(request, tastingNote);
        updateTastingNoteImg(request, tastingNote, multipartFiles);
    }

    private void updateTastingNoteSmellKeyword(TastingNoteRequest.UpdateTastingNoteDTO request, TastingNote tastingNote) {
        if(request.getDeleteSmellKeywordList() != null) smellKeywordTastingNoteRepository.deleteByTastingNoteAndSmellKeywordIn(tastingNote, request.getDeleteSmellKeywordList());
        if(request.getSmellKeywordList() != null) updateSmellKeyword(request, tastingNote);
    }

    private void updateSmellKeyword(TastingNoteRequest.UpdateTastingNoteDTO request, TastingNote tastingNote) {
        List<SmellKeywordTastingNote> smellKeywordTastingNoteList = new ArrayList<>();
        for(SmellKeyword smellKeyword : request.getSmellKeywordList()){
            smellKeywordTastingNoteList.add(tastingNoteConvertor.SmellKeyword(smellKeyword, tastingNote));
        }
        smellKeywordTastingNoteRepository.saveAll(smellKeywordTastingNoteList);
    }

    private void updateTastingNoteImg(TastingNoteRequest.UpdateTastingNoteDTO request, TastingNote tastingNote, List<MultipartFile> multipartFiles) {
        tastingNoteImageUpload(tastingNote, multipartFiles);
        imageDelete(request.getDeleteImgLists());
    }

    private void updateTastingNoteInfo(TastingNoteRequest.UpdateTastingNoteDTO request, TastingNote tastingNote) {
        tastingNoteConvertor.updateTastingNote(tastingNote, request);
        tastingNoteRepository.save(tastingNote);
    }

    private void imageDelete(List<Long> deleteImgLists) {
        if(deleteImgLists != null){
            deleteImgFile(tastingNoteImageRepository.findByIdIn(deleteImgLists));
        }
    }

    private void deleteImgFile(List<TastingNoteImage> tastingNoteImages) {
        for (TastingNoteImage tastingNoteImage : tastingNoteImages){
            s3UploadService.deleteFile(tastingNoteImage.getUrl());
        }
        tastingNoteImageRepository.deleteAllInBatch(tastingNoteImages);
    }

    @Override
    @Transactional
    public TastingNoteResponse.CreateTastingNoteDTO createTastingNote(User user, TastingNoteRequest.CreateTastingNoteDTO request, List<MultipartFile> multipartFiles) {
        Wine wine  = wineRepository.findById(request.getWineId()).orElseThrow(() ->  new BadRequestException(NOT_FOUNT_WINE));
        TastingNote tastingNote = tastingNoteRepository.save(tastingNoteConvertor.CreateTastingNote(request, user, wine));

        if(request.getSmellKeywordList() != null) {
            for (SmellKeyword smellKeyword : request.getSmellKeywordList()) {
                smellKeywordTastingNoteRepository.save(tastingNoteConvertor.SmellKeyword(smellKeyword, tastingNote));
            }
        }

        tastingNoteImageUpload(tastingNote, multipartFiles);

        wineBadgeService.calculateBadge(user, user.getId());

        return new TastingNoteResponse.CreateTastingNoteDTO(tastingNote.getId());
    }

    private void tastingNoteImageUpload(TastingNote tastingNote, List<MultipartFile> multipartFiles) {
        List<String> imgList = s3UploadService.imageListUpload(tastingNote.getId(), TASTING_NOTE, multipartFiles);

        if(imgList!=null) {
            for (String imgUrl : imgList) {
                tastingNoteImageRepository.save(tastingNoteConvertor.TastingImg(tastingNote, imgUrl));
            }
        }
    }

    @Override
    public PageResponse<List<TastingNoteResponse.TastingNoteListDTO>> getTastingNoteList(User user, Integer page, Integer size, Integer order, List<Country> countries, List<WineType> wineTypes, Integer buyAgain) {
        Page<TastingNote> tastingNotes = tastingNoteRepository.findTastingNotes(user, page, size, order, countries, wineTypes, buyAgain);

        return tastingNoteConvertor.TastingNoteList(tastingNotes);
    }
}
