package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "product_file_table")
public class MarketProductFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private MarketProductEntity marketProductEntity;

    public static MarketProductFileEntity toSaveMarketProductFileEntity(MarketProductEntity savedEntity, String originalFileName, String storedFileName) {
        MarketProductFileEntity marketProductFileEntity = new MarketProductFileEntity();
        marketProductFileEntity.setMarketProductEntity(savedEntity);
        marketProductFileEntity.setOriginalFileName(originalFileName);
        marketProductFileEntity.setStoredFileName(storedFileName);
        return marketProductFileEntity;
    }
}