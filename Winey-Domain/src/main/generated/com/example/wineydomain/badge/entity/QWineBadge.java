package com.example.wineydomain.badge.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWineBadge is a Querydsl query type for WineBadge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWineBadge extends EntityPathBase<WineBadge> {

    private static final long serialVersionUID = -1274364411L;

    public static final QWineBadge wineBadge = new QWineBadge("wineBadge");

    public final com.example.wineydomain.common.model.QBaseEntity _super = new com.example.wineydomain.common.model.QBaseEntity(this);

    public final StringPath acquisitionMethod = createString("acquisitionMethod");

    public final StringPath badge = createString("badge");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> requiredActivity = createNumber("requiredActivity", Integer.class);

    public final EnumPath<BadgeType> type = createEnum("type", BadgeType.class);

    public final StringPath unActivatedImgUrl = createString("unActivatedImgUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QWineBadge(String variable) {
        super(WineBadge.class, forVariable(variable));
    }

    public QWineBadge(Path<? extends WineBadge> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWineBadge(PathMetadata metadata) {
        super(WineBadge.class, metadata);
    }

}

