package com.example.wineydomain.image.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWineBadgeImage is a Querydsl query type for WineBadgeImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWineBadgeImage extends EntityPathBase<WineBadgeImage> {

    private static final long serialVersionUID = -1567462898L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWineBadgeImage wineBadgeImage1 = new QWineBadgeImage("wineBadgeImage1");

    public final QImage _super = new QImage(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath url = _super.url;

    public final com.example.wineydomain.badge.entity.QWineBadge wineBadgeImage;

    public QWineBadgeImage(String variable) {
        this(WineBadgeImage.class, forVariable(variable), INITS);
    }

    public QWineBadgeImage(Path<? extends WineBadgeImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWineBadgeImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWineBadgeImage(PathMetadata metadata, PathInits inits) {
        this(WineBadgeImage.class, metadata, inits);
    }

    public QWineBadgeImage(Class<? extends WineBadgeImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.wineBadgeImage = inits.isInitialized("wineBadgeImage") ? new com.example.wineydomain.badge.entity.QWineBadge(forProperty("wineBadgeImage")) : null;
    }

}

