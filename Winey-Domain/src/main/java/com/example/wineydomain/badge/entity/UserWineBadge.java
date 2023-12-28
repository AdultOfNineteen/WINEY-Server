package com.example.wineydomain.badge.entity;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * User와 WineBadge 연결테이블
 */
@Entity
@Table(name = "`UserWineBadge`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class UserWineBadge extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;


    // TODO : 테이블 관리로 변경할 때 이 필드를 ManyToOne WineBadge로 바꿔주어야 합니다.
    @Enumerated(EnumType.STRING)
    private Badge badge;

    @Builder.Default
    private Boolean isRead = Boolean.FALSE;
}
