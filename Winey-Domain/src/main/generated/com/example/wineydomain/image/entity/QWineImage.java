package com.example.wineydomain.image.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWineImage is a Querydsl query type for WineImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWineImage extends EntityPathBase<WineImage> {

    private static final long serialVersionUID = 1113646341L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWineImage wineImage = new QWineImage("wineImage");

    public final QImage _super = new QImage(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath url = _super.url;

    public final com.example.wineydomain.wine.entity.QWine wine;

    public QWineImage(String variable) {
        this(WineImage.class, forVariable(variable), INITS);
    }

    public QWineImage(Path<? extends WineImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWineImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWineImage(PathMetadata metadata, PathInits inits) {
        this(WineImage.class, metadata, inits);
    }

    public QWineImage(Class<? extends WineImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.wine = inits.isInitialized("wine") ? new com.example.wineydomain.wine.entity.QWine(forProperty("wine")) : null;
    }

}

