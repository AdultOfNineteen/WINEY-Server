package com.example.wineydomain.image.entity;

import com.example.wineydomain.wine.entity.Wine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`WineTipImage`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@DiscriminatorValue("WineTipImage")
public class WineTipImage extends Image {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wineTipId", nullable = false)
    private WineTipImage wineTipImage;
}
