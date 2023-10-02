package com.example.wineydomain.wineTip.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWineTip is a Querydsl query type for WineTip
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWineTip extends EntityPathBase<WineTip> {

    private static final long serialVersionUID = 1237687470L;

    public static final QWineTip wineTip = new QWineTip("wineTip");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    public final StringPath blogUrl = createString("blogUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QWineTip(String variable) {
        super(WineTip.class, forVariable(variable));
    }

    public QWineTip(Path<? extends WineTip> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWineTip(PathMetadata metadata) {
        super(WineTip.class, metadata);
    }

}

