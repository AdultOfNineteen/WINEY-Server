package com.example.wineydomain.shop.repository;

import com.example.wineydomain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wineydomain.shop.entity.BookMarkPk;
import com.example.wineydomain.shop.entity.ShopBookMark;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopBookMarkRepository extends JpaRepository<ShopBookMark, BookMarkPk> {
    @Query(value = "select s from ShopBookMark s where s.id.user = :user")
    List<ShopBookMark> findByUser(User user);
}
