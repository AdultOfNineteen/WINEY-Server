package com.example.wineydomain.tastingNote.preference.repository;

import com.example.wineydomain.tastingNote.preference.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
