package com.example.wineydomain.image.entity;

import com.example.wineydomain.badge.entity.WineBadge;
import com.example.wineydomain.wine.entity.Wine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`WineBadgeImage`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@DiscriminatorValue("WineBadgeImage")
public class WineBadgeImage extends Image{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wineBadgeId", nullable = false)
    private WineBadge wineBadgeImage;
}
