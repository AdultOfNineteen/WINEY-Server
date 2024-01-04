package com.example.wineydomain.badge.repository;

import com.example.wineydomain.badge.dto.WineBadgeUserDTO;
import com.example.wineydomain.badge.entity.QUserWineBadge;
import com.example.wineydomain.badge.entity.QWineBadge;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class WineBadgeCustomRepositoryImpl implements WineBadgeCustomRepository {

    private final JPAQueryFactory queryFactory;

    public WineBadgeCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<WineBadgeUserDTO> findWineBadgesWithUser(Long userId) {
        QWineBadge wineBadge = QWineBadge.wineBadge;
        QUserWineBadge userWineBadge = QUserWineBadge.userWineBadge;

        return queryFactory
                .select(Projections.bean(
                        WineBadgeUserDTO.class,
                        wineBadge.id.as("wineBadgeId"),
                        wineBadge.badge,
                        wineBadge.name,
                        wineBadge.type,
                        wineBadge.description,
                        wineBadge.requiredActivity,
                        wineBadge.acquisitionMethod,
                        userWineBadge.user.id.as("userId"),
                        userWineBadge.createdAt.as("acquiredAt"),
                        userWineBadge.isRead.as("isRead")
                ))
                .from(wineBadge)
                .leftJoin(userWineBadge)
                .on(userWineBadge.wineBadge.eq(wineBadge)
                        .and(userWineBadge.user.id.eq(userId)))
                .fetch();
    }



}
