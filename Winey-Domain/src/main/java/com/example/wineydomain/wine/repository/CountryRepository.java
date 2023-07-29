package com.example.wineydomain.wine.repository;

import com.example.wineydomain.wine.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
