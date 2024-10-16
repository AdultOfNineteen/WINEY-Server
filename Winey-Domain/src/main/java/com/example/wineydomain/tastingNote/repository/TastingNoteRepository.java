package com.example.wineydomain.tastingNote.repository;

import com.example.wineydomain.common.model.Status;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineydomain.wine.entity.WineSummary;
import com.example.wineydomain.wine.entity.WineType;
import com.example.wineydomain.wine.repository.WineRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TastingNoteRepository extends JpaRepository<TastingNote, Long>,TastingNoteCustomRepository {
    Integer countByUserAndCreatedAtAfter(User user, LocalDateTime start);

    @Modifying
    @Query("update TastingNote tn set tn.user = null where tn.user.id = :userId")
    void detachUserByUserId(@Param("userId") Long userId);

    @Query("SELECT TN FROM TastingNote TN JOIN FETCH TN.wine where TN.user = :user and TN.isDeleted = false")
    List<TastingNote> findByUser(User user);

    @Query("SELECT new com.example.wineydomain.wine.entity.WineSummary(avg(t.price), avg(t.sweetness), avg(t.acidity), avg(t.body), avg(t.tannins)) FROM TastingNote t WHERE t.wine.id = :wineId AND t.buyAgain = true")
    Optional<WineSummary> findWineSummaryByWineId(@Param("wineId") Long wineId);

    @Query(value = "SELECT W.id as 'id', W.name, W.country, W.type, COALESCE(AVG(CASE WHEN TN.price IS NULL OR TN.price = 0 THEN NULL ELSE TN.price END),0) as 'price', W.varietal " +
            "FROM TastingNote TN JOIN Wine W ON TN.wineId = W.id " +
            "GROUP BY TN.wineId " +
            "ORDER BY COUNT(TN.wineId) DESC, TN.createdAt DESC LIMIT 3",
            nativeQuery = true)
    List<WineRepository.WineList> recommendCountWine();


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

    boolean existsByUserAndBuyAgain(User user, boolean b);

    List<TastingNote> findTop3ByUserOrderByStarRatingAscCreatedAtDesc(User user);

    List<TastingNote> findTop3ByUserOrderByStarRatingDescCreatedAtDesc(User user);

    List<TastingNote> findTop3ByUserAndBuyAgainOrderByStarRatingDescCreatedAtDesc(User user, boolean b);

    Optional<TastingNote> findByUserAndId(User user, Long noteId);

    List<TastingNote> findByUserAndIsDeleted(User user, boolean b);

    List<TastingNote> findByUserAndBuyAgainAndIsDeleted(User user, boolean b, boolean b1);

    Integer countByUserAndCreatedAtAfterAndIsDeleted(User user, LocalDateTime threeMonthsAgo, boolean b);

    boolean existsByUserAndBuyAgainAndIsDeleted(User user, boolean b, boolean b1);

    List<TastingNote> findByUserAndIsDeletedOrderByIdAsc(User user, boolean b);

    @Query("SELECT t FROM TastingNote t WHERE t.wine = :wine AND t.isDeleted = :isDeleted AND (t.isPublic = :isPublic OR t.user = :user) ORDER BY t.id ASC")
    List<TastingNote> findByWineAndIsDeletedAndIsPublicOrderByIdAsc(
        @Param("wine") Wine wine,
        @Param("isDeleted") boolean isDeleted,
        @Param("isPublic") boolean isPublic,
        @Param("user") User user
    );

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
