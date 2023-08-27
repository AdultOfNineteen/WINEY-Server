package com.example.wineydomain.tastingNote.repository;

import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TastingNoteRepository extends JpaRepository<TastingNote, Long>,TastingNoteCustomRepository {
    List<TastingNote> findByUser(User user);



    @Query(value = "select W.id as 'wineId',W.name, W.country, W.type, avg(W.price)'price', W.varietal " +
            "from TastingNote TN join Wine W on TN.wineId = W.id " +
            "group by TN.wineId order by count(TN.wineId) asc, TN.createdAt desc limit 3",
            nativeQuery = true)
    List<WineList> recommendCountWine();

    List<TastingNote> findTop3ByUserOrderByStarRatingDescCreatedAtDesc(User user);

    interface WineList{
        Long getWineId();

        String getName();

        String getCountry();

        String getKind();

        String getVarietal();

        String getType();

        int getPrice();


    }
}
