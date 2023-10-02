package com.example.wineydomain.wine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecommendWinePk is a Querydsl query type for RecommendWinePk
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRecommendWinePk extends BeanPath<RecommendWinePk> {

    private static final long serialVersionUID = 854912911L;

    public static final QRecommendWinePk recommendWinePk = new QRecommendWinePk("recommendWinePk");

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QRecommendWinePk(String variable) {
        super(RecommendWinePk.class, forVariable(variable));
    }

    public QRecommendWinePk(Path<? extends RecommendWinePk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecommendWinePk(PathMetadata metadata) {
        super(RecommendWinePk.class, metadata);
    }

}

