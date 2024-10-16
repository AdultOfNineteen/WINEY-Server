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

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.wineydomain.tastingNote.exception.GetTastingNoteErrorCode.*;
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
        boolean checkTastingNote = tastingNoteRepository.existsByUserAndBuyAgainAndIsDeleted(user, true, false);
        return new TastingNoteResponse.CheckTastingNote(checkTastingNote);
    }

    @Override
    public TastingNoteResponse.TastingNoteDTO getTastingNote(User user, Long noteId, boolean isShared) {
        TastingNote tastingNote = tastingNoteRepository.getTastingNote(noteId, false);
        if(tastingNote == null) throw new BadRequestException(NOT_FOUND_TASTING_NOTE);
        if(tastingNote.getIsPublic().equals(false) && !tastingNote.getUser().getId().equals(user.getId())) throw new BadRequestException(NOT_PUBLIC_TASTING_NOTE);
        Map<Long, Integer> tastingNoteNo;
        if(!isShared) tastingNoteNo = getTastingNoteNo(tastingNote.getUser());
        else tastingNoteNo = getTastingNoteNoByWineId(tastingNote.getWine(), user);
        return tastingNoteConvertor.toTastingNote(tastingNote, tastingNoteNo);
    }

    @Override
    public TastingNoteResponse.NoteFilterDTO getNoteFilter(User user) {
        List<TastingNote> tastingNotes = tastingNoteRepository.findByUserAndIsDeleted(user, false);

        return tastingNoteConvertor.NoteFilter(tastingNotes);
    }

    
  @Override
  @CacheEvict(value = "tastingNoteNoLists", key = "#user.id")
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
        imageDelete(request.getDeleteImgList());
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
    @Caching(evict = {
        @CacheEvict(value = "tastingNoteNoLists", key = "#user.id"),
        @CacheEvict(value = "tastingNoteNoByWineId", key = "#request.wineId + '_' + #user.id")
    })
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
    public PageResponse<List<TastingNoteResponse.TastingNoteListDTO>> getTastingNoteList(User user, Integer page, Integer size, Integer order, List<Country> countries, List<WineType> wineTypes, Integer buyAgain,
        Long wineId) {
        Map<Long,Integer> tastingNoteNo;
        Page<TastingNote> tastingNotes;
        if(wineId ==null) {
            tastingNoteNo = getTastingNoteNo(user);
            tastingNotes = tastingNoteRepository.findTastingNotes(user, page, size, order, countries, wineTypes,
                buyAgain);
        }
        else{
            tastingNotes = tastingNoteRepository.findTastingNotesByWine(wineRepository.findById(wineId).orElseThrow(() -> new BadRequestException(NOT_FOUNT_WINE)),user, page, size);
            tastingNoteNo = getTastingNoteNoByWineId(wineRepository.findById(wineId).orElseThrow(() -> new BadRequestException(NOT_FOUNT_WINE)), user);
        }
        return tastingNoteConvertor.toTastingNoteList(tastingNotes, tastingNoteNo);
    }


    @Cacheable(value = "tastingNoteNoLists", key = "#user.id")
    public Map<Long, Integer> getTastingNoteNo(User user) {
        Map<Long, Integer> tastingNoteNo = new HashMap<>();
        List<TastingNote> tastingNotes = tastingNoteRepository.findByUserAndIsDeletedOrderByIdAsc(user, false);
        for (int i = 0; i < tastingNotes.size(); i++) {
            tastingNoteNo.put(tastingNotes.get(i).getId(), i+1);
        }
        return tastingNoteNo;
    }

    @Cacheable(value = "tastingNoteNoByWineId", key = "#wine.id + '_' + #user.id")
    public Map<Long, Integer> getTastingNoteNoByWineId(Wine wine, User user){
        Map<Long, Integer> tastingNoteNo = new HashMap<>();
        List<TastingNote> tastingNotes = tastingNoteRepository.findByWineAndIsDeletedAndIsPublicOrderByIdAsc(wine, false, true, user);
        for (int i = 0; i < tastingNotes.size(); i++) {
            tastingNoteNo.put(tastingNotes.get(i).getId(), i+1);
        }
        return tastingNoteNo;
    }
}
