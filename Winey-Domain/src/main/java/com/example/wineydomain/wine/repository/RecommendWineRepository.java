package com.example.wineydomain.wine.repository;

import com.example.wineydomain.wine.entity.RecommendWine;
import com.example.wineydomain.wine.entity.RecommendWinePk;
import com.example.wineydomain.wine.entity.Wine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendWineRepository extends JpaRepository<RecommendWine, RecommendWinePk> {
}
