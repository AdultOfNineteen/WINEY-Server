package com.example.wineydomain.shop.entity;

import com.example.wineydomain.common.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Index;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "`Shop`")
@org.hibernate.annotations.Table(
    appliesTo = "`Shop`",
    indexes = {
        @Index(name = "idx_shop_coordinates", columnNames = {"latitude", "longitude"}),
        @Index(name = "idx_shop_type", columnNames = {"shopType"}),
    }
)
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

    private double latitude;

    private double longitude;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    @BatchSize(size = 20)
    private List<ShopMood> shopMoods;

}
