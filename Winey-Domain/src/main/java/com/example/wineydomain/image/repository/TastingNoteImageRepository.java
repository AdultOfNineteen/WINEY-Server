package com.example.wineydomain.image.repository;

import java.util.List;

import com.example.wineydomain.image.entity.TastingNoteImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TastingNoteImageRepository extends JpaRepository<TastingNoteImage, Long> {
	List<TastingNoteImage> findByIdIn(List<Long> deleteImgLists);
}
