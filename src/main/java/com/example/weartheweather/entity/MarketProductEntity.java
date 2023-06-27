package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_table")
@Getter
@Setter
@ToString
public class MarketProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int fileAttached;

    @Column(nullable = false)
    private String productSize;  // 상품 크기

    @Column(nullable = false)
    private String productTitle;  // 글 제목

    @Column(nullable = false)
    private String transactionArea;  // 거래 지역

    @Column(nullable = false)
    private int productPrice;  // 상품 가격

    @Column(nullable = false)
    private String productContents;  // 상품 설명

    @Column(nullable = false)
    private String productSeason;  // 상품 계절

    @Column(nullable = false)
    private String hashTag;  // 해시 태그

    @Column
    private int productHits; // 게시글 조회수

    @Column
    private int productDibs; // 게시글 찜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "marketProductEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MarketProductFileEntity> marketProductFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "marketProductEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AlarmEntity> alarmEntityList = new ArrayList<>();
}