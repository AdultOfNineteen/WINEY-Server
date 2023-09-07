package com.example.wineydomain.tastingNote.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`SmellKeywordTastingNote`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class SmellKeywordTastingNote {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SmellKeyword smellKeyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tastingNoteId", nullable = false)
    private TastingNote tastingNote;
}
