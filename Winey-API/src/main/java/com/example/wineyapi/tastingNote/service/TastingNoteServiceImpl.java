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

import javax.transaction.Transactional;
import java.util.List;

import static com.example.wineydomain.tastingNote.exception.GetTastingNoteErrorCode.NOT_FOUND_TASTING_NOTE;
import static com.example.wineydomain.tastingNote.exception.UploadTastingNoteErrorCode.NOT_FOUNT_WINE;

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
    public TastingNoteResponse.CreateTastingNoteDTO createTastingNote(User user, TastingNoteRequest.CreateTastingNoteDTO request) {
        Wine wine  = wineRepository.findById(request.getWineId()).orElseThrow(() ->  new BadRequestException(NOT_FOUNT_WINE));
        TastingNote tastingNote = tastingNoteRepository.save(tastingNoteConvertor.CreateTastingNote(request, user, wine));

        for(SmellKeyword smellKeyword : request.getSmellKeywordList()){
            smellKeywordTastingNoteRepository.save(tastingNoteConvertor.SmellKeyword(smellKeyword, tastingNote));
        }

        List<String> imgList = s3UploadService.listUploadTastingNote(tastingNote.getId(),request.getMultipartFiles());

        for(String imgUrl : imgList){
            tastingNoteImageRepository.save(tastingNoteConvertor.TastingImg(tastingNote, imgUrl));
        }

        wineBadgeService.calculateBadge(user, user.getId());

        return new TastingNoteResponse.CreateTastingNoteDTO(tastingNote.getId());
    }

    @Override
    public PageResponse<List<TastingNoteResponse.TastingNoteListDTO>> getTastingNoteList(User user, Integer page, Integer size, Integer order, List<Country> countries, List<WineType> wineTypes, Integer buyAgain) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TastingNote> tastingNotes = null;

        if(order.equals(0)){
            if(countries.isEmpty()  && wineTypes.isEmpty()) {
                if(buyAgain == null) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedOrderByCreatedAtDesc(user, false, pageable);
                else if(buyAgain == 1) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainOrderByCreatedAtDesc(user, false, true, pageable);
                else tastingNotes= tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainOrderByCreatedAtDesc(user,false,true, pageable);
            }
            else if(!countries.isEmpty() && wineTypes.isEmpty()){
                if(buyAgain == null) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndWine_CountryInOrderByCreatedAtDesc(user, false, countries, pageable);
                else if(buyAgain.equals(1)) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_CountryInOrderByCreatedAtDesc(user, false, true, countries, pageable);
                else tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_CountryInOrderByCreatedAtDesc(user,false, false, countries, pageable);
            }
            else if(countries.isEmpty()){
                if(buyAgain == null) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndWine_TypeInOrderByCreatedAtDesc(user, false, wineTypes, pageable);
                else if(buyAgain.equals(1)) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_TypeInOrderByCreatedAtDesc(user, false, true, wineTypes, pageable);
                else tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_TypeInOrderByCreatedAtDesc(user,false, false, wineTypes, pageable);
            }
            else{
                if(buyAgain == null) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndWine_CountryInAndWine_TypeInOrderByCreatedAtDesc(user, false, countries ,wineTypes, pageable);
                else if(buyAgain.equals(1)) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_CountryInAndWine_TypeInOrderByCreatedAtDesc(user, false, true,countries ,wineTypes, pageable);
                else tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_CountryInAndWine_TypeInOrderByCreatedAtDesc(user, false, false,countries ,wineTypes, pageable);
            }
        }else{
            if(countries.isEmpty() && wineTypes.isEmpty()) {
                if(buyAgain == null) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedOrderByStarRatingAsc(user, false, pageable);
                else if(buyAgain == 1) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainOrderByStarRatingAsc(user, false, true, pageable);
                else tastingNotes= tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainOrderByStarRatingAsc(user,false,true, pageable);
            }
            else if(!countries.isEmpty() && wineTypes.isEmpty()){
                if(buyAgain == null) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndWine_CountryInOrderByStarRatingAsc(user, false, countries, pageable);
                else if(buyAgain.equals(1)) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_CountryInOrderByStarRatingAsc(user, false, true, countries, pageable);
                else tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_CountryInOrderByStarRatingAsc(user,false, false, countries, pageable);
            }
            else if(countries.isEmpty()){
                if(buyAgain == null) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndWine_TypeInOrderByStarRatingAsc(user, false, wineTypes, pageable);
                else if(buyAgain.equals(1)) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_TypeInOrderByStarRatingAsc(user, false, true, wineTypes, pageable);
                else tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_TypeInOrderByStarRatingAsc(user,false, false, wineTypes, pageable);
            }
            else{
                if(buyAgain == null) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndWine_CountryInAndWine_TypeInOrderByStarRatingAsc(user, false, countries ,wineTypes, pageable);
                else if(buyAgain.equals(1)) tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_CountryInAndWine_TypeInOrderByStarRatingAsc(user, false, true,countries ,wineTypes, pageable);
                else tastingNotes = tastingNoteRepository.findByUserAndIsDeletedAndBuyAgainAndWine_CountryInAndWine_TypeInOrderByStarRatingAsc(user, false, false,countries ,wineTypes, pageable);
            }
        }

        return tastingNoteConvertor.TastingNoteList(tastingNotes);
    }
}
