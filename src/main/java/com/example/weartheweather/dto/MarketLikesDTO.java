package com.example.weartheweather.dto;

import com.example.weartheweather.entity.AdminBoardLikesEntity;
import com.example.weartheweather.entity.MarketLikesEntity;
import com.example.weartheweather.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MarketLikesDTO {

    private Long id;
    private Long memberId;
    private Long boardId;

    private String createdAt;

    public static MarketLikesDTO toDTO(MarketLikesEntity marketLikesEntity) {
        MarketLikesDTO marketLikesDTO = new MarketLikesDTO();
        marketLikesDTO.setId(marketLikesDTO.getId());
        marketLikesDTO.setMemberId(marketLikesEntity.getMemberEntity().getId());
        marketLikesDTO.setBoardId(marketLikesEntity.getMarketProductEntity().getId());
        marketLikesDTO.setCreatedAt(UtilClass.dateFormat(marketLikesEntity.getCreatedAt()));
        return marketLikesDTO;
    }
}
