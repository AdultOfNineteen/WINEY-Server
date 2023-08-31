package com.example.wineydomain.preference.repository;

import com.example.wineydomain.preference.entity.Preference;
import com.example.wineydomain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    List<Preference> findByUser(User user);
}
