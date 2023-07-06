package com.example.weartheweather.repository;

import com.example.weartheweather.entity.MarketProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MarketProductRepository extends JpaRepository<MarketProductEntity, Long > {


    List<MarketProductEntity> findByProductWriter(String productWriter);
}

