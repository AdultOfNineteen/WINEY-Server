package com.example.wineydomain.tastingNote.repository;

import com.example.wineydomain.tastingNote.entity.TastingNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TastingNoteRepository extends JpaRepository<TastingNote, Long> {
}
