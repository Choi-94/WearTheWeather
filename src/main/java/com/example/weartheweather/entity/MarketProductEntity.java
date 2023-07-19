package com.example.weartheweather.entity;

import com.example.weartheweather.dto.MarketProductDTO;
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
public class MarketProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int fileAttached;

    @Column(nullable = false, length = 30)
    private String productWriter;  // 글작성자

    @Column(nullable = false, length = 30)
    private String productSize;  // 상품 크기

    @Column(nullable = false, length = 30)
    private String productTitle;  // 글 제목

    @Column(nullable = false, length = 50)
    private String transactionArea;  // 거래 지역

    @Column(nullable = false, length = 15)
    private Long productPrice;  // 상품 가격

    @Column(nullable = false, length = 15)
    private Long transactionFee;

    @Column(nullable = false, length = 15)
    private Long totalAmount;

    @Column(nullable = false, length = 200)
    private String productContents;  // 상품 설명

    @Column(nullable = false)
    private String productSeason;  // 상품 계절

    @Column(nullable = false)
    private String productWeather;  // 상품 날씨

    @Column(nullable = false)
    private String productTemp;  // 상품 온도

    @Column(nullable = false, length = 100)
    private String productHashtag;  // 해시 태그

    @Column
    private int productHits; // 게시글 조회수

    @Column
    private int marketLikes; // 게시글 찜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "marketProductEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MarketProductFileEntity> marketProductFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "marketProductEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AlarmEntity> alarmEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "marketProductEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MarketLikesEntity> likesEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "marketProductEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MarketPaymentEntity> marketPaymentEntityList = new ArrayList<>();

    public static MarketProductEntity toSaveEntity(MarketProductDTO marketProductDTO, MemberEntity memberEntity) {
        MarketProductEntity marketProductEntity = new MarketProductEntity();
        marketProductEntity.setMemberEntity(memberEntity);
        marketProductEntity.setProductWriter(memberEntity.getMemberNickName());
        marketProductEntity.setProductSize(marketProductDTO.getProductSize());
        marketProductEntity.setProductTitle(marketProductDTO.getProductTitle());
        marketProductEntity.setTransactionArea(marketProductDTO.getTransactionArea());
        marketProductEntity.setProductPrice(marketProductDTO.getProductPrice());
        marketProductEntity.setTransactionFee(Math.round(marketProductDTO.getProductPrice() * 0.03));
        marketProductEntity.setTotalAmount(Math.round(marketProductDTO.getProductPrice() * 0.03) + marketProductDTO.getProductPrice());
        marketProductEntity.setProductContents(marketProductDTO.getProductContents());
        marketProductEntity.setProductSeason(marketProductDTO.getProductSeason());
        marketProductEntity.setProductWeather(marketProductDTO.getProductWeather());
        marketProductEntity.setProductTemp(marketProductDTO.getProductTemp());
        marketProductEntity.setProductHashtag(marketProductDTO.getProductHashtag());
        marketProductEntity.setMarketLikes(0);
        marketProductEntity.setProductHits(0);
        marketProductEntity.setFileAttached(0);
        return  marketProductEntity;
    }
    public static MarketProductEntity toSaveEntityWithFile(MarketProductDTO marketProductDTO, MemberEntity memberEntity) {
        MarketProductEntity marketProductEntity = new MarketProductEntity();
        marketProductEntity.setMemberEntity(memberEntity);
        marketProductEntity.setProductWriter(memberEntity.getMemberNickName());
        marketProductEntity.setProductSize(marketProductDTO.getProductSize());
        marketProductEntity.setProductTitle(marketProductDTO.getProductTitle());
        marketProductEntity.setTransactionArea(marketProductDTO.getTransactionArea());
        marketProductEntity.setProductPrice(marketProductDTO.getProductPrice());
        marketProductEntity.setTransactionFee(Math.round(marketProductDTO.getProductPrice() * 0.03));
        marketProductEntity.setTotalAmount(Math.round(marketProductDTO.getProductPrice() * 0.03) + marketProductDTO.getProductPrice());
        marketProductEntity.setProductContents(marketProductDTO.getProductContents());
        marketProductEntity.setProductSeason(marketProductDTO.getProductSeason());
        marketProductEntity.setProductWeather(marketProductDTO.getProductWeather());
        marketProductEntity.setProductTemp(marketProductDTO.getProductTemp());
        marketProductEntity.setProductHashtag(marketProductDTO.getProductHashtag());
        marketProductEntity.setMarketLikes(0);
        marketProductEntity.setProductHits(0);
        marketProductEntity.setFileAttached(1);
        return  marketProductEntity;
    }

    public static MarketProductEntity toUpdateEntity(MarketProductDTO marketProductDTO, MemberEntity memberEntity) {
        MarketProductEntity marketProductEntity = new MarketProductEntity();
        marketProductEntity.setId(marketProductDTO.getId());
        marketProductEntity.setMemberEntity(memberEntity);
        marketProductEntity.setProductWriter(memberEntity.getMemberNickName());
        marketProductEntity.setProductSize(marketProductDTO.getProductSize());
        marketProductEntity.setProductTitle(marketProductDTO.getProductTitle());
        marketProductEntity.setTransactionArea(marketProductDTO.getTransactionArea());
        marketProductEntity.setProductPrice(marketProductDTO.getProductPrice());
        marketProductEntity.setTransactionFee(Math.round(marketProductDTO.getProductPrice() * 0.03));
        marketProductEntity.setTotalAmount(Math.round(marketProductDTO.getProductPrice() * 0.03) + marketProductDTO.getProductPrice());
        marketProductEntity.setProductContents(marketProductDTO.getProductContents());
        marketProductEntity.setProductSeason(marketProductDTO.getProductSeason());
        marketProductEntity.setProductWeather(marketProductDTO.getProductWeather());
        marketProductEntity.setProductTemp(marketProductDTO.getProductTemp());
        marketProductEntity.setProductHashtag(marketProductDTO.getProductHashtag());
        marketProductEntity.setMarketLikes(0);
        marketProductEntity.setProductHits(0);
        marketProductEntity.setFileAttached(1);
        return  marketProductEntity;
    }
}