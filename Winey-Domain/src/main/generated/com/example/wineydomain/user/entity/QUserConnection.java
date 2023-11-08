package com.example.wineydomain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserConnection is a Querydsl query type for UserConnection
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserConnection extends EntityPathBase<UserConnection> {

    private static final long serialVersionUID = 874813852L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserConnection userConnection = new QUserConnection("userConnection");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QUserConnection(String variable) {
        this(UserConnection.class, forVariable(variable), INITS);
    }

    public QUserConnection(Path<? extends UserConnection> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserConnection(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserConnection(PathMetadata metadata, PathInits inits) {
        this(UserConnection.class, metadata, inits);
    }

    public QUserConnection(Class<? extends UserConnection> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

