package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "report_table")
public class MarketReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String reportReason;  // 신고 사유

    @Column(nullable = false)
    private String reportContents;  // 신고 내용

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private MarketProductEntity marketProductEntity;  // 상품과의 관계 설정

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;  // 상품과의 관계 설정

    // dwwrwwr
}