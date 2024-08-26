package com.example.wineydomain.tastingNote.repository;

import com.example.wineydomain.tastingNote.entity.QTastingNote;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.QWine;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineydomain.wine.entity.WineType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class TastingNoteRepositoryImpl implements TastingNoteCustomRepository{
    private final JPAQueryFactory queryFactory;

    public TastingNote getTastingNote(Long noteId, boolean deleted) {
        QTastingNote qTastingNote = QTastingNote.tastingNote;

        JPAQuery<TastingNote> query = queryFactory
            .selectFrom(qTastingNote)
            .join(qTastingNote.wine).fetchJoin()
            .leftJoin(qTastingNote.tastingNoteImages)
            .leftJoin(qTastingNote.smellKeywordTastingNote)
            .where(isNoteIdAndNotDeleted(qTastingNote, noteId, deleted));

        return query.fetchOne();
    }

    private BooleanExpression isNoteIdAndNotDeleted(QTastingNote qTastingNote, Long noteId, boolean deleted) {
        return qTastingNote.id.eq(noteId).and(qTastingNote.isDeleted.eq(deleted));
    }
    @Override
    public Page<TastingNote> findTastingNotes(User user, Integer page, Integer size, Integer order, List<Country> countries, List<WineType> wineTypes, Integer buyAgain) {
        QTastingNote qTastingNote = QTastingNote.tastingNote;
        QWine qWine = QWine.wine;

        BooleanExpression predicate = qTastingNote.user.eq(user).and(qTastingNote.isDeleted.eq(false));


        if (buyAgain != null) {
            predicate = predicate.and(qTastingNote.buyAgain.eq(buyAgain == 1));
        }

        if (countries != null) {
            predicate = predicate.and(qTastingNote.wine.country.in(countries));
        }

        if (wineTypes != null) {
            predicate = predicate.and(qTastingNote.wine.type.in(wineTypes));
        }

        Pageable pageable = PageRequest.of(page, size);


        JPAQuery<TastingNote> query = queryFactory
                .select(qTastingNote)
                .from(qTastingNote)
                .join(qWine).on(qTastingNote.wine.eq(qWine)).fetchJoin()
                .where(predicate);

        if(order.equals(0)) query.orderBy(qTastingNote.id.desc());
        else query.orderBy(qTastingNote.starRating.desc());

        List<TastingNote> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(qTastingNote).where(predicate).fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<TastingNote> findTastingNotesByWine(Wine wine, User user, Integer page, Integer size) {
        QTastingNote qTastingNote = QTastingNote.tastingNote;
        QWine qWine = QWine.wine;
        Pageable pageable = PageRequest.of(page, size);
        JPAQuery<TastingNote> query = queryFactory
            .select(qTastingNote)
            .from(qTastingNote)
            .join(qWine).on(qTastingNote.wine.eq(qWine)).fetchJoin()
            .where(
                qTastingNote.wine.eq(wine)
                    .and(qTastingNote.isDeleted.eq(false))
                    .and(qTastingNote.isPublic.eq(true)
                        .or(qTastingNote.user.eq(user))
                    )
            )
            .orderBy(qTastingNote.id.desc());

        List<TastingNote> results = query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory.selectFrom(qTastingNote).fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

}
