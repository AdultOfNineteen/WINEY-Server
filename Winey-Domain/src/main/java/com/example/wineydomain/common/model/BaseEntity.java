package com.example.wineydomain.common.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name="createdAt",updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name="updatedAt")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
