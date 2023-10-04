package com.example.wineydomain.tastingNote.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPairing is a Querydsl query type for Pairing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPairing extends EntityPathBase<Pairing> {

    private static final long serialVersionUID = -595521072L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPairing pairing = new QPairing("pairing");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QTastingNote tastingNote;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPairing(String variable) {
        this(Pairing.class, forVariable(variable), INITS);
    }

    public QPairing(Path<? extends Pairing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPairing(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPairing(PathMetadata metadata, PathInits inits) {
        this(Pairing.class, metadata, inits);
    }

    public QPairing(Class<? extends Pairing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tastingNote = inits.isInitialized("tastingNote") ? new QTastingNote(forProperty("tastingNote"), inits.get("tastingNote")) : null;
    }

}

