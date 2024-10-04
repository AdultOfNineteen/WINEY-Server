package com.example.wineyapi.wine.service;

import com.example.wineydomain.redis.entity.RecommendWine;
import com.example.wineydomain.redis.model.RecommendWineDTO;
import com.example.wineydomain.redis.repository.RecommendWineRepository;
import com.example.wineyapi.wine.convertor.WineConvertor;
import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineycommon.exception.NotFoundException;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.preference.entity.Preference;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineydomain.wine.entity.WineSummary;
import com.example.wineydomain.wine.exception.ReadWineErrorCode;
import com.example.wineydomain.wine.repository.WineRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WineServiceImpl implements WineService {
    private final TastingNoteRepository tastingNoteRepository;
    private final WineConvertor wineConvertor;
    private final WineRepository wineRepository;
    private final RecommendWineRepository recommendWineRepository;

    @Override
    public List<RecommendWineDTO> recommendWine(User user) {
        Optional<RecommendWine> recommendWine = recommendWineRepository.findById(String.valueOf(user.getId()));
        if (recommendWine.isPresent()) return recommendWine.get().getRecommendWineList();
        List<TastingNote> tastingNotes = tastingNoteRepository.findTop3ByUserAndBuyAgainOrderByStarRatingDescCreatedAtDesc(user, true);
        List<RecommendWineDTO> recommendWineDTOS = wineConvertor.toRecommendWineDto(findRecommend(user, tastingNotes));
        recommendWineRepository.save(wineConvertor.toRecommendWine(user, recommendWineDTOS));
        return recommendWineDTOS;
    }

    private List<WineRepository.WineList> findRecommend(User user, List<TastingNote> tastingNotes) {
        Preference preferences = user.getPreference();
        List<WineRepository.WineList> recommendWineLists;


        if (preferences == null) {
            if (tastingNotes.size() == 0) {
                recommendWineLists = tastingNoteRepository.recommendCountWine();
            } else {
                Wine wine = tastingNotes.get(0).getWine();
                recommendWineLists = wineRepository.recommendWineByTastingNote(wine.getId(), wine.getAcidity(), wine.getSweetness(), wine.getBody(), wine.getTannins());
            }
        } else {
            if (tastingNotes.size() == 0) {
                recommendWineLists = wineRepository.recommendWine(preferences.getAcidity(), preferences.getSweetness(), preferences.getBody(), preferences.getTannins());
            } else {
                Wine wine = tastingNotes.get(0).getWine();
                recommendWineLists = wineRepository.recommendWineByTastingNote(wine.getId(), wine.getAcidity(), wine.getSweetness(), wine.getBody(), wine.getTannins());
            }
        }


        return recommendWineLists;
    }

    @Override
    @Cacheable(value = "searchedWine", key = "{#page, #size, #content}", cacheManager = "redisCacheManager")
    public PageResponse<List<WineResponse.SearchWineDto>> searchWineList(Integer page, Integer size, String content) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Wine> wines = null;
        if(content == null){
            wines = wineRepository.findAll(pageable);
        }else{
            wines = wineRepository.findByNameContainingOrEngNameContaining(content,content,pageable);
        }
        return wineConvertor.SearchWineList(wines);
    }

    @Override
    public WineResponse.WineDTO getWineDTOById(Long wineId) {
        Wine wine = wineRepository.findById(wineId)
                .orElseThrow(() -> new NotFoundException(ReadWineErrorCode.NOT_EXIST_WINE));
        WineSummary wineSummary = tastingNoteRepository.findWineSummaryByWineId(wineId)
                .orElseGet(() -> new WineSummary(0.0, 0, 0, 0, 0));
        return wineConvertor.toWineDTO(wine, wineSummary);
    }
}
