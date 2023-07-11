package com.example.weartheweather.dto;

import com.example.weartheweather.entity.MarketLikesEntity;
import com.example.weartheweather.entity.MarketPaymentEntity;
import com.example.weartheweather.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@Setter
@ToString
public class MarketPaymentDTO {

    private Long id;
    private Long memberId;
    private String createdAt;
    private String productWriter;
    private int productPrice;
    private String productSize;
    private String productTitle;
    private String deliveryLocation;

}
