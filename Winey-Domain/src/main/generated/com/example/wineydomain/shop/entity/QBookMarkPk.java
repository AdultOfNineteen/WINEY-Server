package com.example.wineydomain.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookMarkPk is a Querydsl query type for BookMarkPk
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBookMarkPk extends BeanPath<BookMarkPk> {

    private static final long serialVersionUID = 76437103L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookMarkPk bookMarkPk = new QBookMarkPk("bookMarkPk");

    public final QShop shop;

    public final com.example.wineydomain.user.entity.QUser user;

    public QBookMarkPk(String variable) {
        this(BookMarkPk.class, forVariable(variable), INITS);
    }

    public QBookMarkPk(Path<? extends BookMarkPk> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookMarkPk(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookMarkPk(PathMetadata metadata, PathInits inits) {
        this(BookMarkPk.class, metadata, inits);
    }

    public QBookMarkPk(Class<? extends BookMarkPk> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shop = inits.isInitialized("shop") ? new QShop(forProperty("shop")) : null;
        this.user = inits.isInitialized("user") ? new com.example.wineydomain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

