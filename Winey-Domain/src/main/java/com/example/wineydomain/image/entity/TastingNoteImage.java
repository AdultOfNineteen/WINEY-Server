package com.example.wineydomain.image.entity;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`TastingNoteImage`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
@DynamicInsert
@DiscriminatorValue("TastingNoteImage")
public class TastingNoteImage extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tastingNoteId", nullable = false)
    private TastingNote tastingNote;
}
