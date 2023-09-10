package com.example.wineydomain.tastingNote.repository;

import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.WineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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

    Page<TastingNote> findByUserAndIsDeletedOrderByCreatedAtDesc(User user, boolean isDeleted, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndBuyAgainOrderByCreatedAtDesc(User user, boolean isDeleted, boolean buyAgain, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndWine_CountryInOrderByCreatedAtDesc(User user, boolean isDeleted, List<Country> countries, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndBuyAgainAndWine_CountryInOrderByCreatedAtDesc(User user, boolean b, boolean b1, List<Country> countries, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndWine_CountryInAndWine_TypeInOrderByCreatedAtDesc(User user, boolean b, List<Country> countries, List<WineType> wineTypes, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndBuyAgainAndWine_CountryInAndWine_TypeInOrderByCreatedAtDesc(User user, boolean b, boolean b1, List<Country> countries, List<WineType> wineTypes, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndWine_TypeInOrderByCreatedAtDesc(User user, boolean b, List<WineType> wineTypes, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndBuyAgainAndWine_TypeInOrderByCreatedAtDesc(User user, boolean b, boolean b1, List<WineType> wineTypes, Pageable pageable);


    Page<TastingNote> findByUserAndIsDeletedOrderByStarRatingAsc(User user, boolean b, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndBuyAgainOrderByStarRatingAsc(User user, boolean b, boolean b1, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndWine_CountryInOrderByStarRatingAsc(User user, boolean b, List<Country> countries, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndBuyAgainAndWine_CountryInOrderByStarRatingAsc(User user, boolean b, boolean b1, List<Country> countries, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndBuyAgainAndWine_TypeInOrderByStarRatingAsc(User user, boolean b, boolean b1, List<WineType> wineTypes, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndWine_CountryInAndWine_TypeInOrderByStarRatingAsc(User user, boolean b, List<Country> countries, List<WineType> wineTypes, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndBuyAgainAndWine_CountryInAndWine_TypeInOrderByStarRatingAsc(User user, boolean b, boolean b1, List<Country> countries, List<WineType> wineTypes, Pageable pageable);

    Page<TastingNote> findByUserAndIsDeletedAndWine_TypeInOrderByStarRatingAsc(User user, boolean b, List<WineType> wineTypes, Pageable pageable);

    List<TastingNote> findByUserAndBuyAgain(User user, boolean b);

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
