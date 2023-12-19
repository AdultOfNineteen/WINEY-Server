package com.example.wineydomain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserExitHistory is a Querydsl query type for UserExitHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserExitHistory extends EntityPathBase<UserExitHistory> {

    private static final long serialVersionUID = -1495457128L;

    public static final QUserExitHistory userExitHistory = new QUserExitHistory("userExitHistory");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath socialId = createString("socialId");

    public final EnumPath<SocialType> socialType = createEnum("socialType", SocialType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QUserExitHistory(String variable) {
        super(UserExitHistory.class, forVariable(variable));
    }

    public QUserExitHistory(Path<? extends UserExitHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserExitHistory(PathMetadata metadata) {
        super(UserExitHistory.class, metadata);
    }

}

