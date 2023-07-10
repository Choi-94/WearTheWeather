package com.example.weartheweather.repository;

import com.example.weartheweather.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MarketLikesRepository extends JpaRepository<MarketLikesEntity, Long> {

    Optional<MarketLikesEntity> findByMarketProductEntityAndMemberEntity(Optional<MarketProductEntity> marketProductEntity, Optional<MemberEntity> memberEntity);
    @Query(value = "select count(b) FROM MarketLikesEntity b where b.marketProductEntity =:marketProductEntity")
    int countMarketLikes(MarketProductEntity marketProductEntity);

    void deleteByMarketProductEntityAndMemberEntity(Optional<MarketProductEntity> marketProductEntity, Optional<MemberEntity> memberEntity);

    List<MarketLikesEntity> findByMemberEntity(MemberEntity memberEntity);
}
