package com.example.wineydomain.tastingNote.entity;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.common.model.Color;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Wine;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`TastingNote`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class TastingNote extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wineId", nullable = false)
    private Wine wine;

    /**
     * 당도
     */
    @Column(name = "sweetness")
    private Integer sweetness;

    /**
     * 산도
     */
    @Column(name = "acidity")
    private Integer acidity;

    /**
     * 바디
     */
    @Column(name = "body")
    private Integer body;

    /**
     * 도수
     */
    @Column(name = "alcohol")
    private Integer alcohol;

    @Column(name = "vintage")
    private Integer vintage;

    /**
     * 탄닌
     */
    @Column(name = "tannins")
    private Integer tannins;

    /**
     * 여운
     */
    @Column(name = "finish")
    private Integer finish;

    @Column(name = "memo")
    private String memo;

    @Column(name = "starRating")
    private Integer starRating;

    @Column(name = "buyAgain")
    private Boolean buyAgain;

    @Column(name = "isDeleted")
    private Boolean isDeleted;
}
