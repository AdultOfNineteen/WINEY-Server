package com.example.wineydomain.wine.entity;

import com.example.wineydomain.common.model.Color;
import com.example.wineydomain.common.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`Wine`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Wine extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "name")
    private String name;

    /**
     * 품종
     */
    @Column(name = "varietal")
    private String varietal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "countryId", nullable = false)
    private Country country;


    @Column(name = "price")
    private Integer price;

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
}
