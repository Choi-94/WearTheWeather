package com.example.weartheweather.dto;

import com.example.weartheweather.entity.MarketProductEntity;
import com.example.weartheweather.entity.MarketProductFileEntity;
import com.example.weartheweather.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MarketProductDTO {
    private Long id;
    private String productSize;
    private String productTitle;
    private String transactionArea;
    private int productPrice;

    private String productContents;
    private String productSeason;
    private String productWeather;
    private String productTemp;
    private String productHashtag;
    private int productDibs;
    private int productHits;
    private String createdAt;

    private List<MultipartFile> productImage;
    private int fileAttached;
    private List<String> originalFileName = new ArrayList<>();
    private List<String> storedFileName = new ArrayList<>();

    public static MarketProductDTO toDTO(MarketProductEntity marketProductEntity) {
        MarketProductDTO marketProductDTO = new MarketProductDTO();
        marketProductDTO.setId(marketProductEntity.getId());
        marketProductDTO.setProductSize(marketProductEntity.getProductSize());
        marketProductDTO.setTransactionArea(marketProductEntity.getTransactionArea());
        marketProductDTO.setProductPrice(marketProductEntity.getProductPrice());
        marketProductDTO.setProductTitle(marketProductEntity.getProductTitle());
        marketProductDTO.setProductContents(marketProductEntity.getProductContents());
        marketProductDTO.setProductHashtag(marketProductEntity.getProductHashtag());
        marketProductDTO.setProductSeason(marketProductEntity.getProductSeason());
        marketProductDTO.setProductWeather(marketProductEntity.getProductWeather());
        marketProductDTO.setProductTemp(marketProductEntity.getProductTemp());
        marketProductDTO.setCreatedAt(UtilClass.dateFormat(marketProductEntity.getCreatedAt()));
        if (marketProductEntity.getFileAttached() == 1) {
            marketProductDTO.setFileAttached(1);
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            for (MarketProductFileEntity marketProductFileEntity : marketProductEntity.getMarketProductFileEntityList()) {
                originalFileNameList.add(marketProductFileEntity.getOriginalFileName());
                storedFileNameList.add(marketProductFileEntity.getStoredFileName());
            }
            marketProductDTO.setOriginalFileName(originalFileNameList);
            marketProductDTO.setStoredFileName(storedFileNameList);
        } else {
            marketProductDTO.setFileAttached(0);
        }
        return marketProductDTO;

    }

        public String getFormattedPrice() {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(this.productPrice) + "원";
        }

    }
