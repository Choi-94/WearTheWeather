package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "admin_transactional_info_table")
public class MemberTradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long weatherPay;

    @Column
    private String buyMember;

    @Column
    private String sellMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_product_id")
    private MarketProductEntity marketProductEntity;
}
