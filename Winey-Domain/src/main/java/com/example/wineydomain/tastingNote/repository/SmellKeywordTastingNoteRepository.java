package com.example.wineydomain.tastingNote.repository;

import java.util.List;

import com.example.wineydomain.tastingNote.entity.SmellKeyword;
import com.example.wineydomain.tastingNote.entity.SmellKeywordTastingNote;
import com.example.wineydomain.tastingNote.entity.TastingNote;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SmellKeywordTastingNoteRepository extends JpaRepository<SmellKeywordTastingNote, Long> {
	void deleteByTastingNoteAndSmellKeyword(TastingNote tastingNote, SmellKeyword smellKeyword);

	void deleteByTastingNoteAndSmellKeywordIn(TastingNote tastingNote, List<SmellKeyword> deleteSmellKeywordList);
}
