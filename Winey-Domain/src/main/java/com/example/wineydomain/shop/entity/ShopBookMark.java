package com.example.wineydomain.shop.entity;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "ShopBookMark")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class ShopBookMark extends BaseEntity {
    @EmbeddedId
    private BookMarkPk id;
}
