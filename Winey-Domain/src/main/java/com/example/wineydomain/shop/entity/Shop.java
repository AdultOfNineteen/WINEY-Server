package com.example.wineydomain.shop.entity;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.tastingNote.entity.SmellKeywordTastingNote;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import org.locationtech.jts.geom.Point;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`Shop`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Shop extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ShopType shopType;

    private String address;

    private String phone;

    private String businessHour;

    private String imgUrl;

    @Column(columnDefinition = "POINT SRID 4326")
    private Point point;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    @BatchSize(size = 20)
    private List<ShopMood> shopMoods;
}
