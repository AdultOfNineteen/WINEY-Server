package com.example.wineydomain.user.entity;

import com.example.wineydomain.common.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`UserExitHistory`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class UserExitHistory extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "socialId")
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SocialType socialType = SocialType.normal;

    @Column(name = "reason", length = 300)
    private String reason;

    public static UserExitHistory from(User user, String reason) {
        return UserExitHistory.builder()
                .nickName(user.getNickName())
                .phoneNumber(user.getPhoneNumber())
                .socialId(user.getSocialId())
                .socialType(user.getSocialType())
                .reason(reason)
                .build();
    }
}
