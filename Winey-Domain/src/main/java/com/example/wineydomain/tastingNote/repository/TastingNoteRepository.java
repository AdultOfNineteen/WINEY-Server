package com.example.wineydomain.tastingNote.repository;

import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TastingNoteRepository extends JpaRepository<TastingNote, Long>,TastingNoteCustomRepository {
    List<TastingNote> findByUser(User user);
}
