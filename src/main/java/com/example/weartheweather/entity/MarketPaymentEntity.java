package com.example.weartheweather.entity;

import com.example.weartheweather.dto.MarketPaymentDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "market_payment_table")
public class MarketPaymentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String productWriter;  // 글작성자

    @Column(nullable = false, length = 15)
    private Long productPrice;  // 상품 가격

    @Column(nullable = false, length = 15)
    private Long transactionFee;

    @Column(nullable = false, length = 15)
    private Long totalAmount;

    @Column(nullable = false, length = 30)
    private String productSize;  // 상품 크기

    @Column(nullable = false, length = 30)
    private String productTitle;  // 글 제목

    @Column(nullable = false, length = 50)
    private String deliveryLocation;  // 배송 지역

    //멤버테이블과 조인(멤버 엔티티에 @OneToMany로 연결해주세요~)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private MemberEntity memberEntity1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private MarketProductEntity marketProductEntity;

    public static MarketPaymentEntity toSaveEntity(MarketPaymentDTO marketPaymentDTO,MemberEntity loginMemberEntity,MemberEntity writerMemberEntity, MarketProductEntity marketProductEntity) {
        MarketPaymentEntity marketPaymentEntity = new MarketPaymentEntity();
        marketPaymentEntity.setProductWriter(marketPaymentDTO.getProductWriter());
        marketPaymentEntity.setProductSize(marketPaymentDTO.getProductSize());
        marketPaymentEntity.setProductPrice(marketPaymentDTO.getProductPrice());
        marketPaymentEntity.setTransactionFee(Math.round(marketPaymentDTO.getProductPrice() * 0.03));
        marketPaymentEntity.setTotalAmount(Math.round(marketPaymentDTO.getProductPrice() * 0.03) + marketPaymentDTO.getProductPrice());
        marketPaymentEntity.setProductTitle(marketPaymentDTO.getProductTitle());
        marketPaymentEntity.setDeliveryLocation(marketPaymentDTO.getDeliveryLocation());
        marketPaymentEntity.setMemberEntity(loginMemberEntity);
        marketPaymentEntity.setMemberEntity1(writerMemberEntity);
        marketPaymentEntity.setMarketProductEntity(marketProductEntity);
        return marketPaymentEntity;
    }
}
