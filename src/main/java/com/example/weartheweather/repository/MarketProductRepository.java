package com.example.weartheweather.repository;

import com.example.weartheweather.entity.MarketProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MarketProductRepository extends JpaRepository<MarketProductEntity, Long > {


    List<MarketProductEntity> findByProductWriter(String productWriter);

    @Modifying
    @Query(value = "update MarketProductEntity b set b.productHits=b.productHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

}

