package com.example.weartheweather.repository;


import com.example.weartheweather.entity.MarketPaymentEntity;
import com.example.weartheweather.entity.MarketProductEntity;
import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketPaymentRepository extends JpaRepository<MarketPaymentEntity, Long> {


    MarketPaymentEntity findByMemberEntityAndMarketProductEntity(MemberEntity memberEntity, MarketProductEntity marketProductEntity);
}
