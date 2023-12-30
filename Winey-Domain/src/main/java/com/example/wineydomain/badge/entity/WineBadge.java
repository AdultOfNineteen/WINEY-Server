package com.example.wineydomain.badge.entity;

import com.example.wineydomain.common.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`WineBadge`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class WineBadge extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "badge")
    private String badge;

    @Enumerated(EnumType.STRING)
    private BadgeType type;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "requiredActivity")
    private Integer requiredActivity;

    @Column(name = "acquisitionMethod")
    private String acquisitionMethod;
}
