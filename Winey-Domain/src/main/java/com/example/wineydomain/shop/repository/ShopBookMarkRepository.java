package com.example.wineydomain.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wineydomain.shop.entity.BookMarkPk;
import com.example.wineydomain.shop.entity.ShopBookMark;

public interface ShopBookMarkRepository extends JpaRepository<ShopBookMark, BookMarkPk> {
}
