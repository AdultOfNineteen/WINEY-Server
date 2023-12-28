package com.example.wineydomain.badge.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserWineBadge is a Querydsl query type for UserWineBadge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserWineBadge extends EntityPathBase<UserWineBadge> {

    private static final long serialVersionUID = 618174714L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserWineBadge userWineBadge = new QUserWineBadge("userWineBadge");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    public final EnumPath<Badge> badge = createEnum("badge", Badge.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRead = createBoolean("isRead");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.example.wineydomain.user.entity.QUser user;

    public QUserWineBadge(String variable) {
        this(UserWineBadge.class, forVariable(variable), INITS);
    }

    public QUserWineBadge(Path<? extends UserWineBadge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserWineBadge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserWineBadge(PathMetadata metadata, PathInits inits) {
        this(UserWineBadge.class, metadata, inits);
    }

    public QUserWineBadge(Class<? extends UserWineBadge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.wineydomain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

