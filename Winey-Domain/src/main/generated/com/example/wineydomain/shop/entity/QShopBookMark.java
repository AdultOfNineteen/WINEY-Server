package com.example.wineydomain.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShopBookMark is a Querydsl query type for ShopBookMark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopBookMark extends EntityPathBase<ShopBookMark> {

    private static final long serialVersionUID = -654439574L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShopBookMark shopBookMark = new QShopBookMark("shopBookMark");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QBookMarkPk id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShopBookMark(String variable) {
        this(ShopBookMark.class, forVariable(variable), INITS);
    }

    public QShopBookMark(Path<? extends ShopBookMark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShopBookMark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShopBookMark(PathMetadata metadata, PathInits inits) {
        this(ShopBookMark.class, metadata, inits);
    }

    public QShopBookMark(Class<? extends ShopBookMark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QBookMarkPk(forProperty("id"), inits.get("id")) : null;
    }

}

