package com.example.wineydomain.wine.entity;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`RecommendWine`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class RecommendWine extends BaseEntity {
    @EmbeddedId
    private RecommendWinePk id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wineId", nullable = false, insertable = false, updatable = false)
    private Wine wine;

}
