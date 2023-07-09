package com.example.wineydomain.tastingNote.entity;

import com.example.wineydomain.common.model.Color;
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
public class TastingNote {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Color color;

    // todo : 향 키워드

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
