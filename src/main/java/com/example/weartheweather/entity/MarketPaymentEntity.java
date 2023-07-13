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

    public static MarketPaymentEntity toSaveEntity(MarketPaymentDTO marketPaymentDTO,MemberEntity loginMemberEntity,MemberEntity sellerMemberEntity, MarketProductEntity marketProductEntity) {
        MarketPaymentEntity marketPaymentEntity = new MarketPaymentEntity();
        marketPaymentEntity.setDeliveryLocation(marketPaymentDTO.getDeliveryLocation());
        marketPaymentEntity.setMemberEntity(loginMemberEntity);
        marketPaymentEntity.setMemberEntity1(sellerMemberEntity);
        marketPaymentEntity.setMarketProductEntity(marketProductEntity);
        return marketPaymentEntity;
    }
}
