package com.example.wineydomain.verificationMessage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVerificationMessage is a Querydsl query type for VerificationMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVerificationMessage extends EntityPathBase<VerificationMessage> {

    private static final long serialVersionUID = -1443300882L;

    public static final QVerificationMessage verificationMessage = new QVerificationMessage("verificationMessage");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> expireAt = createDateTime("expireAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> mismatchAttempts = createNumber("mismatchAttempts", Integer.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final DateTimePath<java.time.LocalDateTime> requestedAt = createDateTime("requestedAt", java.time.LocalDateTime.class);

    public final EnumPath<com.example.wineydomain.common.model.VerifyMessageStatus> status = createEnum("status", com.example.wineydomain.common.model.VerifyMessageStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath verificationNumber = createString("verificationNumber");

    public final DateTimePath<java.time.LocalDateTime> verifiedAt = createDateTime("verifiedAt", java.time.LocalDateTime.class);

    public QVerificationMessage(String variable) {
        super(VerificationMessage.class, forVariable(variable));
    }

    public QVerificationMessage(Path<? extends VerificationMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVerificationMessage(PathMetadata metadata) {
        super(VerificationMessage.class, metadata);
    }

}

