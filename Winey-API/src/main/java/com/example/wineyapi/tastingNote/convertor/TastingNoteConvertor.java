package com.example.wineyapi.tastingNote.convertor;

import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.wine.entity.Wine;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.wineydomain.wine.entity.WineType.RED;

@Component
public class TastingNoteConvertor {

    public WineResponse.TasteAnalysisDTO TasteAnalysis(List<TastingNote> tastingNotes) {
        int totalWineCnt = tastingNotes.size();
        int buyAgainCnt = 0;
        int redCnt = 0;
        int whiteCnt = 0;
        int sparklingCnt = 0;
        int roseCnt = 0;
        int fortifiedCnt = 0;
        int otherCnt = 0;

        String recommendCountry = null;
        String recommendVarietal = null;
        String recommendWineType = null;

        Map<String, Integer> wineCountByCountry = new HashMap<>();
        Map<String, Integer> wineCountByVarietal = new HashMap<>();
        Map<String, Integer> wineCountByType = new HashMap<>();

        Map<String, Integer> top3Country = new HashMap<>();
        Map<String, Integer> top3Varietal = new HashMap<>();

        for (TastingNote tastingNote : tastingNotes){

            Wine wine = tastingNote.getWine();

            String country  = wine.getCountry().getValue();

            String varietal = wine.getVarietal();

            if(tastingNote.getBuyAgain()){
                buyAgainCnt += 1;
            }
            switch (wine.getType()){
                case RED:
                    redCnt += 1;
                    break;
                case WHITE:
                    whiteCnt += 1;
                    break;
                case SPARKLING:
                    sparklingCnt += 1;
                    break;
                case ROSE:
                    roseCnt += 1;
                    break;
                case FORTIFIED:
                    fortifiedCnt += 1;
                    break;
                case OTHER:
                    otherCnt += 1;
                    break;
            }

            wineCountByCountry.put(country, wineCountByCountry.getOrDefault(country, 0) + 1);
            wineCountByVarietal.put(varietal, wineCountByVarietal.getOrDefault(varietal,0) + 1);
            wineCountByType.put(wine.getType().getValue(), wineCountByType.getOrDefault(wine.getType().getValue() ,0)+1);
        }

        List<Map.Entry<String, Integer>> sortCountry = new ArrayList<>(wineCountByCountry.entrySet());
        if (!sortCountry.isEmpty()) {
            recommendCountry = sortCountry.get(0).getKey();
        }

        List<Map.Entry<String, Integer>> sortType = new ArrayList<>(wineCountByType.entrySet());
        if (!sortType.isEmpty()) {
            recommendWineType = sortCountry.get(0).getKey();
        }

        List<Map.Entry<String, Integer>> sortVarietal = new ArrayList<>(wineCountByVarietal.entrySet());
        if (!sortVarietal.isEmpty()) {
            recommendVarietal = sortCountry.get(0).getKey();
        }

        for (Map.Entry<String, Integer> country : sortCountry) {
            top3Country.put(country.getKey(), country.getValue());
        }

        for (Map.Entry<String, Integer> varietal : sortVarietal) {
            top3Varietal.put(varietal.getKey(), varietal.getValue());
        }

        return WineResponse.TasteAnalysisDTO.builder()
                .recommendCountry(recommendCountry)
                .recommendVarietal(recommendVarietal)
                .recommendWineType(recommendWineType)
                .totalWineCnt(totalWineCnt)
                .buyAgainCnt(buyAgainCnt)
                .redCnt(redCnt)
                .whiteCnt(whiteCnt)
                .sparklingCnt(sparklingCnt)
                .roseCnt(roseCnt)
                .fortifiedCnt(fortifiedCnt)
                .otherCnt(otherCnt)
                .top3Country(top3Country)
                .top3Varietal(top3Varietal)
                .build();
    }
}
