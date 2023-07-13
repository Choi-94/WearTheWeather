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
    private Long buyerId;
    private Long sellerId;
    private Long productId;
    private String createdAt;
    private String sellerWriter;
    private String deliveryLocation;
    private int tradeStatus;

    public static MarketPaymentDTO toDTO(MarketPaymentEntity marketPaymentEntity) {
        MarketPaymentDTO marketPaymentDTO = new MarketPaymentDTO();
        marketPaymentDTO.setId(marketPaymentEntity.getId());
        marketPaymentDTO.setBuyerId(marketPaymentEntity.getMemberEntity().getId());
        marketPaymentDTO.setSellerId(marketPaymentEntity.getMemberEntity1().getId());
        marketPaymentDTO.setCreatedAt(UtilClass.dateFormat(marketPaymentEntity.getCreatedAt()));
        marketPaymentDTO.setDeliveryLocation(marketPaymentEntity.getDeliveryLocation());
        marketPaymentDTO.setTradeStatus(marketPaymentEntity.getTradeStatus());
        return marketPaymentDTO;
    }

}
