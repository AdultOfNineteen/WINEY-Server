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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

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
        List<TastingNote> tastingNotes = tastingNoteRepository.findByUserAndBuyAgain(user, true);

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
        TastingNote tastingNote = tastingNoteRepository.findByUserAndId(user, noteId).orElseThrow(()-> new BadRequestException(NOT_FOUND_TASTING_NOTE));

        tastingNote.setIsDeleted(true);

        deleteImgFile(tastingNote.getTastingNoteImages());

        tastingNoteRepository.save(tastingNote);
    }

    private void deleteImgFile(List<TastingNoteImage> tastingNoteImages) {
        for (TastingNoteImage tastingNoteImage : tastingNoteImages){
            s3UploadService.deleteFile(tastingNoteImage.getUrl());
        }

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

        List<String> imgList = s3UploadService.imageListUpload(tastingNote.getId(), TASTING_NOTE, multipartFiles);

        if(imgList!=null) {
            for (String imgUrl : imgList) {
                tastingNoteImageRepository.save(tastingNoteConvertor.TastingImg(tastingNote, imgUrl));
            }
        }

        wineBadgeService.calculateBadge(user, user.getId());

        return new TastingNoteResponse.CreateTastingNoteDTO(tastingNote.getId());
    }

    @Override
    public PageResponse<List<TastingNoteResponse.TastingNoteListDTO>> getTastingNoteList(User user, Integer page, Integer size, Integer order, List<Country> countries, List<WineType> wineTypes, Integer buyAgain) {
        Page<TastingNote> tastingNotes = tastingNoteRepository.findTastingNotes(user, page, size, order, countries, wineTypes, buyAgain);

        return tastingNoteConvertor.TastingNoteList(tastingNotes);
    }
}
