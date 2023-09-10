package com.example.wineyapi.wine.service;

import com.example.wineyapi.tastingNote.convertor.TastingNoteConvertor;
import com.example.wineyapi.wine.convertor.WineConvertor;
import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.preference.entity.Preference;
import com.example.wineydomain.preference.repository.PreferenceRepository;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineydomain.wine.repository.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WineServiceImpl implements WineService {
    private final TastingNoteRepository tastingNoteRepository;
    private final PreferenceRepository preferenceRepository;
    private final WineConvertor wineConvertor;
    private final TastingNoteConvertor tastingNoteConvertor;
    private final WineRepository wineRepository;
    @Override
    public List<WineResponse.RecommendWineDTO> recommendWine(User user) {
        Preference preferences = user.getPreference();
        List<TastingNote> tastingNotes = tastingNoteRepository.findTop3ByUserOrderByStarRatingDescCreatedAtDesc(user);


        /*
        1. 취향설정X * 노트작성X : 가장 많이 데이터가 쌓인 (=가장 많은 유저들이 마셔본) 와인 정보 노출
        2. 취향설정X * 노트작성 O * 추천 X : 사용자가 작성한 테이스팅 노트 중 가장 점수가 높은 와인과 비슷한 와인 정보 노출
        3. 취향설정O * 노트작성 O * 추천X : 위와 동일
        4. 취향설정O * 노트 작성 X : 설정한 취향과 비슷한 와인 정보 노출
        */

        List<WineResponse.RecommendWineDTO> recommendWineDTO = null;

        Pageable pageable = PageRequest.of(0, 3);


        if (preferences == null) {
            if (tastingNotes.size() == 0) {
                List<TastingNoteRepository.WineList> wineLists = tastingNoteRepository.recommendCountWine();
                recommendWineDTO = wineConvertor.RecommendWineCountByWine(wineLists);
            } else {
                Wine wine = tastingNotes.get(0).getWine();
                List<Wine> wines = wineRepository.recommendWineByTastingNote(wine.getId(), wine.getAcidity(), wine.getSweetness(), wine.getBody(), wine.getTannins(), pageable);
                recommendWineDTO = wineConvertor.RecommendWineByTastingNote(wines);
            }
        } else {
            if (tastingNotes.size() == 0) {
                List<Wine> wines = wineRepository.recommendWine(preferences.getAcidity(), preferences.getSweetness(), preferences.getBody(), preferences.getTannins(), pageable);
                System.out.println(wines.size());
                recommendWineDTO = wineConvertor.RecommendWineByTastingNote(wines);
            } else {
                Wine wine = tastingNotes.get(0).getWine();
                List<Wine> wines = wineRepository.recommendWineByTastingNote(wine.getId(), wine.getAcidity(), wine.getSweetness(), wine.getBody(), wine.getTannins(), pageable);
                System.out.println(wines.size());
                recommendWineDTO = wineConvertor.RecommendWineByTastingNote(wines);
            }
        }


        return recommendWineDTO;
    }

    @Override
    public WineResponse.TasteAnalysisDTO tasteAnalysis(User user) {
        List<TastingNote> tastingNotes = tastingNoteRepository.findByUser(user);


        return tastingNoteConvertor.TasteAnalysis(tastingNotes);
    }

    @Override
    public PageResponse<List<WineResponse.SearchWineDto>> searchWineList(Integer page, Integer size, String content) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Wine> wines = null;
        if(content == null){
            wines = wineRepository.findAll(pageable);
        }else{
            wines = wineRepository.findByNameContaining(content,pageable);
        }
        return wineConvertor.SearchWineList(wines);
    }

    @Override
    public WineResponse.WineDTO getWineById(Long wineId) {
        return null;
    }
}
