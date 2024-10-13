package com.example.wineydomain.tastingNote.entity;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.image.entity.TastingNoteImage;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Wine;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`TastingNote`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@BatchSize(size = 100)
public class TastingNote extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wineId", nullable = false)
    private Wine wine;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "tastingNoteId")
    @BatchSize(size = 20)
    private List<SmellKeywordTastingNote> smellKeywordTastingNote;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "tastingNoteId")
    @BatchSize(size = 10)
    private List<TastingNoteImage> tastingNoteImages;

    private Double officialAlcohol;

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

    @Column(name ="sparkling")
    private Integer sparkling;

    /**
     * 여운
     */
    @Column(name = "finish")
    private Integer finish;

    private Integer price;

    @Column(name = "memo")
    private String memo;

    @Column(name = "starRating")
    private Integer starRating;

    @Column(name = "buyAgain")
    private Boolean buyAgain;

    @Column(name = "isDeleted")
    private Boolean isDeleted;

    @Column(name = "isPublic")
    private Boolean isPublic;
}

