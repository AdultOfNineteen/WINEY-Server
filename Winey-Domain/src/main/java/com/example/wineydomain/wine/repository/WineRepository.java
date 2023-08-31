package com.example.wineydomain.wine.repository;

import com.example.wineydomain.wine.entity.Wine;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WineRepository extends JpaRepository<Wine, Long> {
    @Query("SELECT w FROM Wine w where w.id != :id and " +
            "((w.acidity = :acidity and w.sweetness = :sweetness and w.body = :body and w.tannins = :tannins) or " +
            "(w.acidity != :acidity and w.sweetness = :sweetness and w.body = :body and w.tannins = :tannins) or " +
            "(w.acidity = :acidity and w.sweetness != :sweetness and w.body = :body and w.tannins = :tannins) or " +
            "(w.acidity = :acidity and w.sweetness = :sweetness and w.body != :body and w.tannins = :tannins) or" +
            "(w.acidity = :acidity and w.sweetness = :sweetness and w.body = :body and w.tannins != :tannins))")
    List<Wine> recommendWineByTastingNote(@Param("id") Long id, @Param("acidity") Integer acidity, @Param("sweetness") Integer sweetness, @Param("body") Integer body, @Param("tannins") Integer tannins, Pageable pageable);

    @Query("SELECT w FROM Wine w where" +
            "((w.acidity = :acidity and w.sweetness = :sweetness and w.body = :body and w.tannins = :tannins) or " +
            "(w.acidity != :acidity and w.sweetness = :sweetness and w.body = :body and w.tannins = :tannins) or " +
            "(w.acidity = :acidity and w.sweetness != :sweetness and w.body = :body and w.tannins = :tannins) or " +
            "(w.acidity = :acidity and w.sweetness = :sweetness and w.body != :body and w.tannins = :tannins) or" +
            "(w.acidity = :acidity and w.sweetness = :sweetness and w.body = :body and w.tannins != :tannins))")
    List<Wine> recommendWine(@Param("acidity") Integer acidity, @Param("sweetness") Integer sweetness, @Param("body") Integer body, @Param("tannins") Integer tannins, Pageable pageable);
}
