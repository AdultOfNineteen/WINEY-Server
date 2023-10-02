package com.example.wineydomain.wine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWine is a Querydsl query type for Wine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWine extends EntityPathBase<Wine> {

    private static final long serialVersionUID = -2073598054L;

    public static final QWine wine = new QWine("wine");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    public final NumberPath<Integer> acidity = createNumber("acidity", Integer.class);

    public final NumberPath<Integer> alcohol = createNumber("alcohol", Integer.class);

    public final NumberPath<Integer> body = createNumber("body", Integer.class);

    public final StringPath color = createString("color");

    public final EnumPath<Country> country = createEnum("country", Country.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath region = createString("region");

    public final NumberPath<Integer> sweetness = createNumber("sweetness", Integer.class);

    public final NumberPath<Integer> tannins = createNumber("tannins", Integer.class);

    public final EnumPath<WineType> type = createEnum("type", WineType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath varietal = createString("varietal");

    public final NumberPath<Integer> vintage = createNumber("vintage", Integer.class);

    public QWine(String variable) {
        super(Wine.class, forVariable(variable));
    }

    public QWine(Path<? extends Wine> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWine(PathMetadata metadata) {
        super(Wine.class, metadata);
    }

}

