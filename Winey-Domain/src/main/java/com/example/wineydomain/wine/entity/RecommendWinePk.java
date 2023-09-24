package com.example.wineydomain.wine.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendWinePk implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="userId")
    private Long userId;

    @Column(name = "wineId")
    private Long projectId;
}
