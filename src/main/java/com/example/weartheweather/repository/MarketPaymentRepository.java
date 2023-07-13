package com.example.weartheweather.repository;


import com.example.weartheweather.entity.MarketPaymentEntity;
import com.example.weartheweather.entity.MarketProductEntity;
import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MarketPaymentRepository extends JpaRepository<MarketPaymentEntity, Long> {


    MarketPaymentEntity findByMemberEntityAndMarketProductEntity(MemberEntity memberEntity, MarketProductEntity marketProductEntity);


    List<MarketPaymentEntity> findByMemberEntityOrMemberEntity1(MemberEntity memberEntity,MemberEntity memberEntity1);
}
