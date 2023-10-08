package com.example.wineydomain.tastingNote.repository;

import com.example.wineydomain.image.entity.QTastingNoteImage;
import com.example.wineydomain.tastingNote.entity.QSmellKeywordTastingNote;
import com.example.wineydomain.tastingNote.entity.QTastingNote;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.wine.entity.QWine;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TastingNoteRepositoryImpl implements TastingNoteCustomRepository{
    private final JPAQueryFactory queryFactory;

    public TastingNote getTastingNote(Long noteId, boolean deleted){
        QTastingNote qTastingNote = QTastingNote.tastingNote;

        JPAQuery<TastingNote> query = queryFactory
                .selectFrom(qTastingNote)
                .join(qTastingNote.wine).fetchJoin() // 페치 조인 사용
                .leftJoin(qTastingNote.tastingNoteImages)
                .leftJoin(qTastingNote.smellKeywordTastingNote).fetchJoin()
                .where(qTastingNote.id.eq(noteId).and(qTastingNote.isDeleted.eq(deleted)));

        return query.fetchOne();
    }



}
