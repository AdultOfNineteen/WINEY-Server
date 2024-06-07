package com.example.wineydomain.tastingNote.repository;

import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.WineType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TastingNoteCustomRepository {
    TastingNote getTastingNote(Long noteId, boolean deleted, User user);

    Page<TastingNote> findTastingNotes(User user, Integer page, Integer size, Integer order, List<Country> countries, List<WineType> wineTypes, Integer buyAgain);
}
