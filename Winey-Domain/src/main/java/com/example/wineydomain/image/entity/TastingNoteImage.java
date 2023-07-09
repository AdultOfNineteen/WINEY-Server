package com.example.wineydomain.image.entity;

import com.example.wineydomain.tastingNote.entity.TastingNote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@DynamicInsert
@DiscriminatorValue("TastingNoteImage")
public class TastingNoteImage extends Image{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tastingNoteId", nullable = false)
    private TastingNote tastingNote;
}
