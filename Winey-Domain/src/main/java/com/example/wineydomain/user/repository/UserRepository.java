package com.example.wineydomain.user.repository;

import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, UserCustomRepository {

    Optional<User> findBySocialIdAndSocialType(String socialId, SocialType socialType);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
