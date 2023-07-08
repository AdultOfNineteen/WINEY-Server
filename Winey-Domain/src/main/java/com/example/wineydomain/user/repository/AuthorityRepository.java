package com.example.wineydomain.user.repository;


import com.example.wineydomain.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}