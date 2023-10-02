package com.example.wineydomain.tastingNote.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSmellKeywordTastingNote is a Querydsl query type for SmellKeywordTastingNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSmellKeywordTastingNote extends EntityPathBase<SmellKeywordTastingNote> {

    private static final long serialVersionUID = 370703984L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSmellKeywordTastingNote smellKeywordTastingNote = new QSmellKeywordTastingNote("smellKeywordTastingNote");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<SmellKeyword> smellKeyword = createEnum("smellKeyword", SmellKeyword.class);

    public final QTastingNote tastingNote;

    public QSmellKeywordTastingNote(String variable) {
        this(SmellKeywordTastingNote.class, forVariable(variable), INITS);
    }

    public QSmellKeywordTastingNote(Path<? extends SmellKeywordTastingNote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSmellKeywordTastingNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSmellKeywordTastingNote(PathMetadata metadata, PathInits inits) {
        this(SmellKeywordTastingNote.class, metadata, inits);
    }

    public QSmellKeywordTastingNote(Class<? extends SmellKeywordTastingNote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tastingNote = inits.isInitialized("tastingNote") ? new QTastingNote(forProperty("tastingNote"), inits.get("tastingNote")) : null;
    }

}

