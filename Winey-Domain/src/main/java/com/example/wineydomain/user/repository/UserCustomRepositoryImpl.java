package com.example.wineydomain.user.repository;

import com.example.wineydomain.common.WineGrade;
import com.example.wineydomain.tastingNote.entity.QTastingNote;
import com.example.wineydomain.user.entity.QUser;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

public class UserCustomRepositoryImpl implements UserCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateWineGradeForAllUsers() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QUser user = QUser.user;
        QTastingNote tastingNote = QTastingNote.tastingNote;

        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        // WINERY 등급으로 업데이트
        queryFactory
                .update(user)
                .set(user.wineGrade, WineGrade.WINERY)
                .where(user.id.in(
                        JPAExpressions.select(tastingNote.user.id)
                                .from(tastingNote)
                                .where(tastingNote.createdAt.after(threeMonthsAgo.atStartOfDay()).and(tastingNote.isDeleted.eq(false)))
                                .groupBy(tastingNote.user)
                                .having(tastingNote.count().goe(WineGrade.WINERY.getMinCount()))
                ))
                .execute();

        // OAK 등급으로 업데이트
        queryFactory
                .update(user)
                .set(user.wineGrade, WineGrade.OAK)
                .where(user.id.in(
                        JPAExpressions.select(tastingNote.user.id)
                                .from(tastingNote)
                                .where(tastingNote.createdAt.after(threeMonthsAgo.atStartOfDay()).and(tastingNote.isDeleted.eq(false)))
                                .groupBy(tastingNote.user)
                                .having(tastingNote.count().goe(WineGrade.OAK.getMinCount()),
                                        tastingNote.count().lt(WineGrade.WINERY.getMinCount()))
                ))
                .execute();

        // BOTTLE 등급으로 업데이트
        queryFactory
                .update(user)
                .set(user.wineGrade, WineGrade.BOTTLE)
                .where(user.id.in(
                        JPAExpressions.select(tastingNote.user.id)
                                .from(tastingNote)
                                .where(tastingNote.createdAt.after(threeMonthsAgo.atStartOfDay()).and(tastingNote.isDeleted.eq(false)))
                                .groupBy(tastingNote.user)
                                .having(tastingNote.count().goe(WineGrade.BOTTLE.getMinCount()),
                                        tastingNote.count().lt(WineGrade.OAK.getMinCount()))
                ))
                .execute();

        // GLASS 등급으로 업데이트 (나머지 모든 사용자)
        queryFactory
                .update(user)
                .set(user.wineGrade, WineGrade.GLASS)
                .where(user.id.in(
                        JPAExpressions.select(tastingNote.user.id)
                                .from(tastingNote)
                                .where(tastingNote.createdAt.after(threeMonthsAgo.atStartOfDay()).and(tastingNote.isDeleted.eq(false)))
                                .groupBy(tastingNote.user)
                                .having(tastingNote.count().lt(WineGrade.BOTTLE.getMinCount()))
                ))
                .execute();
    }


}
