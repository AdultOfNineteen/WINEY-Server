package com.example.wineydomain.tastingNote.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTastingNote is a Querydsl query type for TastingNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTastingNote extends EntityPathBase<TastingNote> {

    private static final long serialVersionUID = -1658173266L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTastingNote tastingNote = new QTastingNote("tastingNote");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    public final NumberPath<Integer> acidity = createNumber("acidity", Integer.class);

    public final NumberPath<Integer> alcohol = createNumber("alcohol", Integer.class);

    public final NumberPath<Integer> body = createNumber("body", Integer.class);

    public final BooleanPath buyAgain = createBoolean("buyAgain");

    public final StringPath color = createString("color");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> finish = createNumber("finish", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final StringPath memo = createString("memo");

    public final NumberPath<Double> officialAlcohol = createNumber("officialAlcohol", Double.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<SmellKeywordTastingNote, QSmellKeywordTastingNote> smellKeywordTastingNote = this.<SmellKeywordTastingNote, QSmellKeywordTastingNote>createList("smellKeywordTastingNote", SmellKeywordTastingNote.class, QSmellKeywordTastingNote.class, PathInits.DIRECT2);

    public final NumberPath<Integer> sparkling = createNumber("sparkling", Integer.class);

    public final NumberPath<Integer> starRating = createNumber("starRating", Integer.class);

    public final NumberPath<Integer> sweetness = createNumber("sweetness", Integer.class);

    public final NumberPath<Integer> tannins = createNumber("tannins", Integer.class);

    public final ListPath<com.example.wineydomain.image.entity.TastingNoteImage, com.example.wineydomain.image.entity.QTastingNoteImage> tastingNoteImages = this.<com.example.wineydomain.image.entity.TastingNoteImage, com.example.wineydomain.image.entity.QTastingNoteImage>createList("tastingNoteImages", com.example.wineydomain.image.entity.TastingNoteImage.class, com.example.wineydomain.image.entity.QTastingNoteImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.example.wineydomain.user.entity.QUser user;

    public final NumberPath<Integer> vintage = createNumber("vintage", Integer.class);

    public final com.example.wineydomain.wine.entity.QWine wine;

    public QTastingNote(String variable) {
        this(TastingNote.class, forVariable(variable), INITS);
    }

    public QTastingNote(Path<? extends TastingNote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTastingNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTastingNote(PathMetadata metadata, PathInits inits) {
        this(TastingNote.class, metadata, inits);
    }

    public QTastingNote(Class<? extends TastingNote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.wineydomain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
        this.wine = inits.isInitialized("wine") ? new com.example.wineydomain.wine.entity.QWine(forProperty("wine")) : null;
    }

}

