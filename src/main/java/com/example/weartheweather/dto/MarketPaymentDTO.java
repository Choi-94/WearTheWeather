package com.example.weartheweather.dto;

import com.example.weartheweather.entity.MarketLikesEntity;
import com.example.weartheweather.entity.MarketPaymentEntity;
import com.example.weartheweather.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MarketPaymentDTO {

    private Long id;
    private Long memberId;
    private Long boardId;

    private String createdAt;

    public static MarketPaymentDTO toDTO(MarketPaymentEntity marketPaymentEntity) {
        MarketPaymentDTO marketLikesDTO = new MarketPaymentDTO();
        marketLikesDTO.setId(marketLikesDTO.getId());
        marketLikesDTO.setMemberId(marketPaymentEntity.getMemberEntity().getId());
        marketLikesDTO.setBoardId(marketPaymentEntity.getMarketProductEntity().getId());
        marketLikesDTO.setCreatedAt(UtilClass.dateFormat(marketPaymentEntity.getCreatedAt()));
        return marketLikesDTO;
    }
}
