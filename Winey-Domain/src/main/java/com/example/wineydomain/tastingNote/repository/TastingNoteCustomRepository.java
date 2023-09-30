package com.example.wineydomain.tastingNote.repository;

import com.example.wineydomain.tastingNote.entity.TastingNote;

public interface TastingNoteCustomRepository {
    TastingNote getTastingNote(Long noteId, boolean deleted);
}
