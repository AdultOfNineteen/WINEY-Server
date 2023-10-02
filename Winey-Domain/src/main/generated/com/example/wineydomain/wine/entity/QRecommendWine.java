package com.example.wineydomain.wine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendWine is a Querydsl query type for RecommendWine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendWine extends EntityPathBase<RecommendWine> {

    private static final long serialVersionUID = 1511502452L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendWine recommendWine = new QRecommendWine("recommendWine");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QRecommendWinePk id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.example.wineydomain.user.entity.QUser user;

    public final QWine wine;

    public QRecommendWine(String variable) {
        this(RecommendWine.class, forVariable(variable), INITS);
    }

    public QRecommendWine(Path<? extends RecommendWine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendWine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendWine(PathMetadata metadata, PathInits inits) {
        this(RecommendWine.class, metadata, inits);
    }

    public QRecommendWine(Class<? extends RecommendWine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QRecommendWinePk(forProperty("id")) : null;
        this.user = inits.isInitialized("user") ? new com.example.wineydomain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
        this.wine = inits.isInitialized("wine") ? new QWine(forProperty("wine")) : null;
    }

}

