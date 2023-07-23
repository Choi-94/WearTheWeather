package com.example.weartheweather.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;



    public String calculateElapsedDays() {
        LocalDate today = LocalDate.now();
        long elapsedDays = ChronoUnit.DAYS.between(createdAt.toLocalDate(), today);
        if (elapsedDays == 0) {
            return "오늘";
        } else if (elapsedDays == 1) {
            return "어제";
        } else {
            return elapsedDays + "일 전";
        }
    }
}
