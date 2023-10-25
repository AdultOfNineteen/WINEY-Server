package com.example.wineydomain.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "UserAuthority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "authorityName")
    private Authority authority;

    // 추가적인 필드와 getter, setter 등이 들어갈 수 있습니다.
}
