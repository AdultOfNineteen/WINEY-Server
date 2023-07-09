package com.example.wineydomain.image.entity;

import com.example.wineydomain.wine.entity.Wine;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`WineImage`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@DiscriminatorValue("WineImage")
public class WineImage extends Image {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wineId", nullable = false)
    private Wine wine;
}
