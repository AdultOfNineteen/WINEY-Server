package com.example.wineydomain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserFcmToken is a Querydsl query type for UserFcmToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserFcmToken extends EntityPathBase<UserFcmToken> {

    private static final long serialVersionUID = 202932935L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserFcmToken userFcmToken = new QUserFcmToken("userFcmToken");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath deviceId = createString("deviceId");

    public final StringPath fcmToken = createString("fcmToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QUserFcmToken(String variable) {
        this(UserFcmToken.class, forVariable(variable), INITS);
    }

    public QUserFcmToken(Path<? extends UserFcmToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserFcmToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserFcmToken(PathMetadata metadata, PathInits inits) {
        this(UserFcmToken.class, metadata, inits);
    }

    public QUserFcmToken(Class<? extends UserFcmToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

