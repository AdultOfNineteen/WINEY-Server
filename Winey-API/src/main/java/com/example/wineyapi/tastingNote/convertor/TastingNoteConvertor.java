package com.example.wineyapi.tastingNote.convertor;

import com.example.wineyapi.tastingNote.dto.TastingNoteRequest;
import com.example.wineyapi.tastingNote.dto.TastingNoteResponse;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.image.entity.TastingNoteImage;
import com.example.wineydomain.tastingNote.entity.SmellKeyword;
import com.example.wineydomain.tastingNote.entity.SmellKeywordTastingNote;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineydomain.wine.entity.WineType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class TastingNoteConvertor {

    public TastingNoteResponse.TasteAnalysisDTO TasteAnalysis(List<TastingNote> tastingNotes) {
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

        List<TastingNoteResponse.Top3Country> top3Country = new ArrayList<>();
        List<TastingNoteResponse.Top3Varietal>top3Varietal =new ArrayList<>();
        List<TastingNoteResponse.Top7Smell> top7Smell = new ArrayList<>();

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
            String[] varietalLists = varietal.split(", ");
            for(int i = 0; i < varietalLists.length ; i++){
                wineCountByVarietal.put(varietalLists[i], wineCountByVarietal.getOrDefault(varietalLists[i],0) + 1);
            }
            wineCountByType.put(wine.getType().getValue(), wineCountByType.getOrDefault(wine.getType().getValue() ,0)+1);
            for(SmellKeywordTastingNote smellKeywordTastingNote : tastingNote.getSmellKeywordTastingNote()) {
                wineCountBySmell.put(smellKeywordTastingNote.getSmellKeyword().getName(), wineCountBySmell.getOrDefault(smellKeywordTastingNote.getSmellKeyword().getName(), 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortCountry = new ArrayList<>(wineCountByCountry.entrySet());

        if (!sortCountry.isEmpty()) {
            recommendCountry = sortCountry.get(0).getKey();
        }

        List<Map.Entry<String, Integer>> sortType = new ArrayList<>(wineCountByType.entrySet());
        Collections.sort(sortType, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
        if (!sortType.isEmpty()) {
            recommendWineType = sortType.get(0).getKey();
        }

        List<Map.Entry<String, Integer>> sortVarietal = new ArrayList<>(wineCountByVarietal.entrySet());
        if (!sortVarietal.isEmpty()) {
            recommendVarietal = sortVarietal.get(0).getKey();
        }

        List<Map.Entry<String, Integer>> sortSmellKeyword = new ArrayList<>(wineCountBySmell.entrySet());

        Collections.sort(sortSmellKeyword, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });


        for (Map.Entry<String, Integer> varietal : sortSmellKeyword) {
            totalSmellCount += varietal.getValue();
        }

        for (int i = 0; i < Math.min(3, sortCountry.size()); i++) {
            top3Country.add(
                    TastingNoteResponse.Top3Country
                            .builder()
                            .country(sortCountry.get(i).getKey())
                            .count(sortCountry.get(i).getValue())
                    .build());
        }

        for (int i = 0; i < Math.min(3, sortVarietal.size()); i++) {
            double cntPercent = calculateAvgPercent(sortVarietal.get(i).getValue(),totalWineCnt);
            top3Varietal.add(
                    TastingNoteResponse.Top3Varietal
                            .builder()
                            .varietal(sortVarietal.get(i).getKey())
                            .percent((int) (Math.round(cntPercent * 10.0) / 10.0))
                            .build());
        }

        for (int i = 0; i < Math.min(7, sortSmellKeyword.size()); i++) {
            double cntPercent = (double) sortSmellKeyword.get(i).getValue() / totalSmellCount * 100.0;
            top7Smell.add(
                    TastingNoteResponse.Top7Smell
                            .builder()
                            .smell(sortSmellKeyword.get(i).getKey())
                            .percent((int) (Math.round(cntPercent * 10.0) / 10.0))
                            .build());
        }

        double avgPriceDouble =  calculateAvg(totalPrice, totalWineCnt);
        int avgPrice = (int) (Math.round(avgPriceDouble * 10.0) / 10.0);

        return TastingNoteResponse.TasteAnalysisDTO.builder()
                .recommendCountry(recommendCountry)
                .recommendVarietal(recommendVarietal)
                .recommendWineType(recommendWineType)
                .totalWineCnt(totalWineCnt)
                .buyAgainCnt(buyAgainCnt)
                .top3Country(top3Country)
                .top3Varietal(top3Varietal)
                .top7Smell(top7Smell)
                .avgPrice(avgPrice)
                .top3Type(calculateWineTypesPercent(sortType))
                .taste(TastingNoteResponse.Taste
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

    private List<TastingNoteResponse.Top3Type> calculateWineTypesPercent(List<Map.Entry<String, Integer>> sortType) {
        List<TastingNoteResponse.Top3Type> top3Types = new ArrayList<>();
        int top3Count = 0;

        for (int i = 0; i < Math.min(3, sortType.size()); i++){
            top3Count += sortType.get(i).getValue();
        }

        for (int i = 0; i < Math.min(3, sortType.size()); i++) {
            top3Types.add(new TastingNoteResponse.Top3Type(sortType.get(i).getKey(), (int) calculateAvgPercent(sortType.get(i).getValue(), top3Count)));
        }
        return top3Types;
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


    public TastingNote CreateTastingNote(TastingNoteRequest.CreateTastingNoteDTO request, User user, Wine wine) {
        return TastingNote.builder()
                .wine(wine)
                .color(request.getColor())
                .user(user)
                .officialAlcohol(request.getOfficialAlcohol())
                .sweetness(request.getSweetness())
                .acidity(request.getAcidity())
                .body(request.getBody())
                .alcohol(request.getAlcohol())
                .vintage(request.getVintage())
                .tannins(request.getTannin())
                .finish(request.getFinish())
                .price(request.getPrice())
                .memo(request.getMemo())
                .buyAgain(request.getBuyAgain())
                .starRating(request.getRating())
                .isDeleted(false)
                .build();
    }

    public SmellKeywordTastingNote SmellKeyword(SmellKeyword smellKeyword, TastingNote tastingNote) {
        return SmellKeywordTastingNote.builder()
                .smellKeyword(smellKeyword)
                .tastingNote(tastingNote)
                .build();
    }

    public TastingNoteImage TastingImg(TastingNote tastingNote, String imgUrl) {
        return TastingNoteImage
                .builder()
                .tastingNote(tastingNote)
                .url(imgUrl)
                .build();
    }

    public PageResponse<List<TastingNoteResponse.TastingNoteListDTO>> TastingNoteList(Page<TastingNote> tastingNotes) {
        List<TastingNoteResponse.TastingNoteListDTO> tastingNoteListDTOS = new ArrayList<>();

        tastingNotes.getContent().forEach(
                result -> {
                    tastingNoteListDTOS.add(
                    new TastingNoteResponse.TastingNoteListDTO(
                            result.getId(),
                            result.getWine().getName(),
                            result.getWine().getCountry(),
                            result.getStarRating(),
                            result.getBuyAgain(),
                            result.getWine().getType()
                    ));
                }
        );

        return new PageResponse<>( tastingNotes.isLast(),tastingNotes.getTotalElements(), tastingNoteListDTOS);
    }

    public TastingNoteResponse.TastingNoteDTO TastingNote(TastingNote tastingNote) {
        Wine wine = tastingNote.getWine();
        List<TastingNoteImage> tastingNoteImages = tastingNote.getTastingNoteImages();
        List<SmellKeywordTastingNote> smellKeywordTastingNotes = tastingNote.getSmellKeywordTastingNote();
        LocalDateTime createdAt = tastingNote.getCreatedAt();

        return TastingNoteResponse.TastingNoteDTO
                .builder()
                .noteId(tastingNote.getId())
                .noteDate(createdAt.getYear()+"."+createdAt.getMonthValue()+"."+createdAt.getDayOfMonth())
                .wineType(wine.getType())
                .wineName(wine.getName())
                .region(wine.getRegion())
                .star(tastingNote.getStarRating())
                .color(tastingNote.getColor())
                .buyAgain(tastingNote.getBuyAgain())
                .varietal(wine.getVarietal())
                .price(tastingNote.getPrice())
                .officialAlcohol(tastingNote.getOfficialAlcohol())
                .smellKeywordList(SmellKeywordList(smellKeywordTastingNotes))
                .myWineTaste(MyWineTaste(tastingNote))
                .defaultWineTaste(DefaultWineTaste(wine))
                .tastingNoteImage(TastingNoteImageRes(tastingNoteImages))
                .memo(tastingNote.getMemo())
                .build();
    }

    private List<TastingNoteResponse.TastingNoteImage> TastingNoteImageRes(List<TastingNoteImage> tastingNoteImages) {
        List<TastingNoteResponse.TastingNoteImage> tastingNoteImageList = new ArrayList<>();

        for (TastingNoteImage tastingNoteImage : tastingNoteImages) {
            tastingNoteImageList.add(
                    new TastingNoteResponse.TastingNoteImage(tastingNoteImage.getId(), tastingNoteImage.getUrl())
            );
        }
        return tastingNoteImageList;
    }

    private TastingNoteResponse.DefaultWineTaste DefaultWineTaste(Wine wine) {
        return TastingNoteResponse.DefaultWineTaste
                .builder()
                .sweetness(wine.getSweetness())
                .acidity(wine.getAcidity())
                .body(wine.getBody())
                .tannin(wine.getTannins())
                .build();
    }

    private TastingNoteResponse.MyWineTaste MyWineTaste(TastingNote tastingNote) {
        return TastingNoteResponse.MyWineTaste
                .builder()
                .sweetness(tastingNote.getSweetness())
                .acidity(tastingNote.getAcidity())
                .alcohol(tastingNote.getAlcohol())
                .body(tastingNote.getBody())
                .tannin(tastingNote.getTannins())
                .finish(tastingNote.getFinish())
                .build();
    }

    private List<String> SmellKeywordList(List<SmellKeywordTastingNote> smellKeywordTastingNotes) {
        List<String> smellKeywordList = new ArrayList<>();

        for(SmellKeywordTastingNote smellKeywordTastingNote : smellKeywordTastingNotes){
            smellKeywordList.add(smellKeywordTastingNote.getSmellKeyword().getName());
        }

        return smellKeywordList;
    }

    public TastingNoteResponse.NoteFilterDTO NoteFilter(List<TastingNote> tastingNotes) {
        Map<WineType, Integer> wineCountByType = new HashMap<>();
        Map<Country, Integer> wineCountByCountry = new HashMap<>();

        for(TastingNote tastingNote : tastingNotes){
            Wine wine = tastingNote.getWine();
            Country country  = wine.getCountry();
            wineCountByCountry.put(country, wineCountByCountry.getOrDefault(country, 0) + 1);
            wineCountByType.put(wine.getType(), wineCountByType.getOrDefault(wine.getType() ,0)+1);
        }

        for (WineType type : WineType.values()) {
            wineCountByType.putIfAbsent(type, 0);
        }

        // 모든 Country Enum 값을 순회하면서 없는 경우 0으로 설정
        for (Country country : Country.values()) {
            wineCountByCountry.putIfAbsent(country, 0);
        }

        return TastingNoteResponse.NoteFilterDTO
                .builder()
                .wineTypes(wineTypesFilter(wineCountByType,tastingNotes.size()))
                .countries(countryFilter(wineCountByCountry,tastingNotes.size()))
                .build();
    }

    private List<TastingNoteResponse.WineTypeFilter> wineTypesFilter(Map<WineType, Integer> wineCountByType, int size) {
        List<TastingNoteResponse.WineTypeFilter> wineTypeFilters = new ArrayList<>();
        wineTypeFilters.add(new TastingNoteResponse.WineTypeFilter("전체", checkValue(size)));
        for (Map.Entry<WineType, Integer> entry : wineCountByType.entrySet()) {
            wineTypeFilters.add(new TastingNoteResponse.WineTypeFilter(entry.getKey().getName(), checkValue(entry.getValue())));
        }
        return wineTypeFilters;
    }

    private List<TastingNoteResponse.CountryFilter> countryFilter(Map<Country, Integer> wineCountByCountry, int size) {
        List<TastingNoteResponse.CountryFilter> countryFilters = new ArrayList<>();
        countryFilters.add(new TastingNoteResponse.CountryFilter("전체", checkValue(size)));
        for (Map.Entry<Country, Integer> entry : wineCountByCountry.entrySet()) {
            countryFilters.add(new TastingNoteResponse.CountryFilter(entry.getKey().getValue(), checkValue(entry.getValue())));
        }
        return countryFilters;
    }

    private String checkValue(int value){
        if(value > 100){
            return "100+";
        }
        else {
            return String.valueOf(value);
        }
    }
}
