package com.example.wineydomain.user.repository;

import com.example.wineydomain.common.WineGrade;
import com.example.wineydomain.tastingNote.entity.QTastingNote;
import com.example.wineydomain.user.entity.QUser;
import com.querydsl.core.types.dsl.CaseBuilder;
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

        // 사용자별 테이스팅 노트 개수를 계산하는 서브쿼리
        JPQLQuery<Long> noteCountSubQuery = JPAExpressions.select(tastingNote.count())
                .from(tastingNote)
                .where(tastingNote.user.eq(user)
                        .and(tastingNote.createdAt.after(threeMonthsAgo.atStartOfDay())));

        // 사용자 등급 업데이트
        queryFactory.update(user)
                .set(user.wineGrade, new CaseBuilder()
                        .when(noteCountSubQuery.goe(Long.valueOf(WineGrade.WINERY.getMinCount())))
                        .then(WineGrade.WINERY)
                        .when(noteCountSubQuery.goe(Long.valueOf(WineGrade.OAK.getMinCount()))
                                .and(noteCountSubQuery.lt(Long.valueOf(WineGrade.WINERY.getMinCount()))))
                        .then(WineGrade.OAK)
                        .when(noteCountSubQuery.goe(Long.valueOf(WineGrade.BOTTLE.getMinCount()))
                                .and(noteCountSubQuery.lt(Long.valueOf(WineGrade.OAK.getMinCount()))))
                        .then(WineGrade.BOTTLE)
                        .otherwise(WineGrade.GLASS))
                .execute();
    }

}
