package com.example.wineydomain.preference.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPreference is a Querydsl query type for Preference
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPreference extends EntityPathBase<Preference> {

    private static final long serialVersionUID = -1873787970L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPreference preference = new QPreference("preference");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    public final NumberPath<Integer> acidity = createNumber("acidity", Integer.class);

    public final NumberPath<Integer> body = createNumber("body", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> sweetness = createNumber("sweetness", Integer.class);

    public final NumberPath<Integer> tannins = createNumber("tannins", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.example.wineydomain.user.entity.QUser user;

    public QPreference(String variable) {
        this(Preference.class, forVariable(variable), INITS);
    }

    public QPreference(Path<? extends Preference> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPreference(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPreference(PathMetadata metadata, PathInits inits) {
        this(Preference.class, metadata, inits);
    }

    public QPreference(Class<? extends Preference> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.wineydomain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

