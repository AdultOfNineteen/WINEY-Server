package com.example.wineydomain.wine.repository;

import com.example.wineydomain.wine.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WineRepository extends JpaRepository<Wine, Long> {
    List<Wine> findTop3ByIdNotAndBodyAndTanninsAndSweetnessAndAcidityAndVarietal(Long id, Integer body, Integer tannins, Integer sweetness, Integer acidity, String varietal);

    List<Wine> findTop3ByOrderByCreatedAt();
    List<Wine> findByIdGreaterThanEqual(Long id);

    List<Wine> findTop3BySweetnessAndAcidityAndBodyAndTannins(Integer sweetness, Integer acidity, Integer body, Integer tannins);
}
