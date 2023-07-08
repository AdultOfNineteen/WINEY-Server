package com.example.wineydomain.user.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "Authority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "authorityName", length = 50)
    private String authorityName;
}
