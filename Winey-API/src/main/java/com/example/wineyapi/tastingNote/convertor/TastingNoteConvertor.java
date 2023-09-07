package com.example.wineyapi.tastingNote.convertor;

import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineydomain.tastingNote.entity.SmellKeywordTastingNote;
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
        int totalSmellCount = 0;
        int sweetnessSum = 0;
        int aciditySum = 0;
        int alcoholSum = 0;
        int bodySum = 0;
        int tanninSum = 0;
        int finishSum = 0;
        int totalPrice = 0;

        String recommendCountry = null;
        String recommendVarietal = null;
        String recommendWineType = null;

        Map<String, Integer> wineCountByCountry = new HashMap<>();
        Map<String, Integer> wineCountByVarietal = new HashMap<>();
        Map<String, Integer> wineCountByType = new HashMap<>();
        Map<String, Integer> wineCountBySmell = new HashMap<>();

        List<Map<String, String>> top3Country = new ArrayList<>();
        List<Map<String, String>>top3Varietal =new ArrayList<>();
        List<Map<String, String>> top7Smell = new ArrayList<>();


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

            sweetnessSum += tastingNote.getSweetness();
            aciditySum += tastingNote.getAcidity();
            alcoholSum += tastingNote.getAlcohol();
            bodySum += tastingNote.getBody();
            tanninSum += tastingNote.getTannins();
            finishSum += tastingNote.getFinish();
            totalPrice += tastingNote.getPrice();


            wineCountByCountry.put(country, wineCountByCountry.getOrDefault(country, 0) + 1);
            wineCountByVarietal.put(varietal, wineCountByVarietal.getOrDefault(varietal,0) + 1);
            wineCountByType.put(wine.getType().getValue(), wineCountByType.getOrDefault(wine.getType().getValue() ,0)+1);
            for(SmellKeywordTastingNote smellKeywordTastingNote : tastingNote.getSmellKeywordTastingNote()) {
                wineCountBySmell.put(smellKeywordTastingNote.getSmellKeyword().getValue(), wineCountBySmell.getOrDefault(smellKeywordTastingNote.getSmellKeyword().getValue(), 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortCountry = new ArrayList<>(wineCountByCountry.entrySet());
        if (!sortCountry.isEmpty()) {
            recommendCountry = sortCountry.get(0).getKey();
        }

        List<Map.Entry<String, Integer>> sortType = new ArrayList<>(wineCountByType.entrySet());
        if (!sortType.isEmpty()) {
            recommendWineType = sortType.get(0).getKey();
        }

        List<Map.Entry<String, Integer>> sortVarietal = new ArrayList<>(wineCountByVarietal.entrySet());
        if (!sortVarietal.isEmpty()) {
            recommendVarietal = sortVarietal.get(0).getKey();
        }

        List<Map.Entry<String, Integer>> sortSmellKeyword = new ArrayList<>(wineCountBySmell.entrySet());

        for (Map.Entry<String, Integer> varietal : sortSmellKeyword) {
            totalSmellCount += varietal.getValue();
        }

        for (int i = 0; i < Math.min(3, sortCountry.size()); i++) {
            HashMap<String,String> country = new HashMap<>();
            country.put("country", sortCountry.get(i).getKey());
            double wineCountPercent = calculateAvgPercent(sortCountry.get(i).getValue(),totalWineCnt);
            String percentString = String.format("%.1f", Math.round(wineCountPercent * 10.0) / 10.0);
            country.put("percent", percentString);
            top3Country.add(country);
        }

        for (int i = 0; i < Math.min(3, sortVarietal.size()); i++) {
            HashMap<String,String> varietal = new HashMap<>();
            varietal.put("varietal", sortVarietal.get(i).getKey());
            double cntPercent = calculateAvgPercent(sortVarietal.get(i).getValue(),totalWineCnt);
            String percentString = String.format("%.1f", Math.round(cntPercent * 10.0) / 10.0);
            varietal.put("percent", percentString);
            top3Varietal.add(varietal);
        }

        for (int i = 0; i < Math.min(7, sortSmellKeyword.size()); i++) {
            HashMap<String,String> smell = new HashMap<>();
            smell.put("smell", sortSmellKeyword.get(i).getKey());
            double cntPercent = (double) sortSmellKeyword.get(i).getValue() / totalSmellCount * 100.0;
            String percentString = String.format("%.1f", Math.round(cntPercent * 10.0) / 10.0);
            smell.put("percent", percentString);
            top7Smell.add(smell);
        }

        double avgPriceDouble =  calculateAvg(totalPrice, totalWineCnt);
        int avgPrice = (int) (Math.round(avgPriceDouble * 10.0) / 10.0);

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
                .top7Smell(top7Smell)
                .avgPrice(avgPrice)
                .taste(WineResponse.Taste
                        .builder()
                        .sweetness(calculateAvg(sweetnessSum, totalWineCnt))
                        .acidity(calculateAvg(aciditySum, totalWineCnt))
                        .alcohol(calculateAvg(alcoholSum, totalWineCnt))
                        .body(calculateAvg(bodySum, totalWineCnt))
                        .tannin(calculateAvg(tanninSum, totalWineCnt))
                        .finish(calculateAvg(finishSum, totalWineCnt))
                        .build())
                .build();
    }

    public double calculateAvg(int sum, int totalCnt){
        if(totalCnt ==0){
            return 0;
        }
        return (double) sum /totalCnt;

    }

    public double calculateAvgPercent(int sum, int totalCnt){
        if(totalCnt ==0){
            return 0;
        }
        return (double) sum /totalCnt * 100.0;

    }


}
