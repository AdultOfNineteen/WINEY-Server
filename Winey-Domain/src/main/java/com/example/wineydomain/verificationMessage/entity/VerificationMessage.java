package com.example.wineydomain.verificationMessage.entity;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.common.model.VerifyMessageStatus;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "`VerificationMessage`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class VerificationMessage extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "verificationNumber")
    private String verificationNumber;

    @Column(name = "expireAt")
    private LocalDateTime expireAt;

    @Column(name = "requestedAt")
    private LocalDateTime requestedAt;

    @Column(name = "verifiedAt")
    private LocalDateTime verifiedAt;

    @Enumerated(EnumType.STRING)
    private VerifyMessageStatus status;

    @Column(name = "mismatchAttempts")
    private Integer mismatchAttempts;

    @Column(name = "requestCount")
    @Builder.Default
    private Integer requestCount = 0;
}
