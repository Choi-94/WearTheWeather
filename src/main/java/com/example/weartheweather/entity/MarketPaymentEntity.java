package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity

@Table(name = "payment_table")
public class MarketPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String transactionType;  // 거래 유형

    @Column(nullable = false)
    private String address;  // 주소

    @Column(nullable = false)
    private String deliveryRequest;  // 배송 요청사항

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private MarketProductEntity marketProductEntity;



}