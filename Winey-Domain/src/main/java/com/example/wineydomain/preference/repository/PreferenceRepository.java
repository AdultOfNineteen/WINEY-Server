package com.example.wineydomain.preference.repository;

import com.example.wineydomain.preference.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
