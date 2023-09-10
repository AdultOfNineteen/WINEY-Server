package com.example.wineydomain.wine.repository;

import com.example.wineydomain.wine.entity.Wine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WineRepository extends JpaRepository<Wine, Long> {
    @Query(value = "SELECT w.id, w.name, w.country, w.type, COALESCE(AVG(NULLIF(TN.price, 0)), 0) as 'price', w.varietal " +
            "FROM Wine w left join TastingNote TN on w.id = TN.wineId where w.id = 424 and  w.id != :id or " +
            "((w.acidity = :acidity and w.sweetness = :sweetness and w.body = :body and w.tannins = :tannins) or " +
            "(w.acidity != :acidity and w.sweetness = :sweetness and w.body = :body and w.tannins = :tannins) or " +
            "(w.acidity = :acidity and w.sweetness != :sweetness and w.body = :body and w.tannins = :tannins) or " +
            "(w.acidity = :acidity and w.sweetness = :sweetness and w.body != :body and w.tannins = :tannins) or" +
            "(w.acidity = :acidity and w.sweetness = :sweetness and w.body = :body and w.tannins != :tannins))" +
            " group by w.id order by rand() limit 3"
            ,nativeQuery = true)
    List<WineList> recommendWineByTastingNote(@Param("id") Long id, @Param("acidity") Integer acidity, @Param("sweetness") Integer sweetness, @Param("body") Integer body, @Param("tannins") Integer tannins);

    @Query(value = "SELECT w.id, w.name, w.country, w.type, COALESCE(AVG(NULLIF(TN.price, 0)), 0) as 'price', w.varietal " +
            "FROM Wine w LEFT JOIN TastingNote TN ON w.id = TN.wineId " +
            "WHERE w.id = :id AND " +
            "((w.acidity = :acidity AND w.sweetness = :sweetness AND w.body = :body AND w.tannins = :tannins) OR " +
            "(w.acidity != :acidity AND w.sweetness = :sweetness AND w.body = :body AND w.tannins = :tannins) OR " +
            "(w.acidity = :acidity AND w.sweetness != :sweetness AND w.body = :body AND w.tannins = :tannins) OR " +
            "(w.acidity = :acidity AND w.sweetness = :sweetness AND w.body != :body AND w.tannins = :tannins) OR " +
            "(w.acidity = :acidity AND w.sweetness = :sweetness AND w.body = :body AND w.tannins != :tannins)) " +
            "GROUP BY w.id " +
            "ORDER BY RAND() LIMIT 3",
            nativeQuery = true)

    List<WineList> recommendWine(@Param("acidity") Integer acidity, @Param("sweetness") Integer sweetness, @Param("body") Integer body, @Param("tannins") Integer tannins);

    Page<Wine> findByNameContaining(String content, Pageable pageable);

    interface WineList{
        Long getId();

        String getName();

        String getCountry();

        String getKind();

        String getVarietal();

        String getType();

        int getPrice();


    }
}
