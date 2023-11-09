package com.example.wineydomain.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShopMood is a Querydsl query type for ShopMood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopMood extends EntityPathBase<ShopMood> {

    private static final long serialVersionUID = -1433643253L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShopMood shopMood = new QShopMood("shopMood");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Mood> mood = createEnum("mood", Mood.class);

    public final QShop shop;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShopMood(String variable) {
        this(ShopMood.class, forVariable(variable), INITS);
    }

    public QShopMood(Path<? extends ShopMood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShopMood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShopMood(PathMetadata metadata, PathInits inits) {
        this(ShopMood.class, metadata, inits);
    }

    public QShopMood(Class<? extends ShopMood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shop = inits.isInitialized("shop") ? new QShop(forProperty("shop")) : null;
    }

}

