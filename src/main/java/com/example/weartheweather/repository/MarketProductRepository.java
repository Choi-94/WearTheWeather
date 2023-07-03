package com.example.weartheweather.repository;

import com.example.weartheweather.entity.MarketProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketProductRepository extends JpaRepository<MarketProductEntity, Long> {

}
