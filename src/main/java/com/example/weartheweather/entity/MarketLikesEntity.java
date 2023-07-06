package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "market_likes_table")
public class MarketLikesEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //멤버테이블과 조인(멤버 엔티티에 @OneToMany로 연결해주세요~)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private MarketProductEntity marketProductEntity;

    public static MarketLikesEntity toSaveEntity(MemberEntity memberEntity, MarketProductEntity marketProductEntity) {
        MarketLikesEntity marketLikesEntity = new MarketLikesEntity();
        marketLikesEntity.setMemberEntity(memberEntity);
        marketLikesEntity.setMarketProductEntity(marketProductEntity);
        return marketLikesEntity;
    }
}
