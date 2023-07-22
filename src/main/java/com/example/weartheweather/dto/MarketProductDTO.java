package com.example.weartheweather.dto;

import com.example.weartheweather.entity.MarketProductEntity;
import com.example.weartheweather.entity.MarketProductFileEntity;
import com.example.weartheweather.entity.MemberEntity;
import com.example.weartheweather.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
public class MarketProductDTO {
    private Long id;
    private Long memberId;

    private String productWriter;
    private String productSize;
    private String productTitle;
    private String transactionArea;
    private Long productPrice;
    private Long transactionFee;
    private Long totalAmount;

    private String productContents;
    private String productSeason;
    private String productWeather;
    private String productTemp;
    private String productHashtag;
    private int marketLikes;
    private int productHits;
    private String createdAt;
    private String calculateElapsedDays;
    private List<MultipartFile> productImage;
    private int fileAttached;
    private List<String> originalFileName = new ArrayList<>();
    private List<String> storedFileName = new ArrayList<>();

    public static MarketProductDTO toDTO(MarketProductEntity marketProductEntity) {
        MarketProductDTO marketProductDTO = new MarketProductDTO();
        marketProductDTO.setId(marketProductEntity.getId());
        marketProductDTO.setProductWriter(marketProductEntity.getProductWriter());
        marketProductDTO.setProductSize(marketProductEntity.getProductSize());
        marketProductDTO.setTransactionArea(marketProductEntity.getTransactionArea());
        marketProductDTO.setProductPrice(marketProductEntity.getProductPrice());
        marketProductDTO.setTransactionFee(marketProductEntity.getTransactionFee());
        marketProductDTO.setTotalAmount(marketProductEntity.getTotalAmount());
        marketProductDTO.setProductTitle(marketProductEntity.getProductTitle());
        marketProductDTO.setProductContents(marketProductEntity.getProductContents());
        marketProductDTO.setProductHashtag(marketProductEntity.getProductHashtag());
        marketProductDTO.setProductSeason(marketProductEntity.getProductSeason());
        marketProductDTO.setProductWeather(marketProductEntity.getProductWeather());
        marketProductDTO.setProductTemp(marketProductEntity.getProductTemp());
        marketProductDTO.setMarketLikes(marketProductEntity.getMarketLikes());
        marketProductDTO.setProductHits(marketProductEntity.getProductHits());
        marketProductDTO.setMemberId(marketProductEntity.getMemberEntity().getId());
        marketProductDTO.setCreatedAt(UtilClass.dateFormat(marketProductEntity.getCreatedAt()));
        marketProductDTO.setCalculateElapsedDays(marketProductEntity.calculateElapsedDays());
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

    public String getTransactionFee() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        double transactionFee = this.productPrice * 0.03;
        return "+" + decimalFormat.format(transactionFee) + "원";
    }

    public String getTotalAmount2() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        double transactionFee = this.productPrice * 0.03;
        double totalAmount = this.productPrice + transactionFee;
        return "+" + decimalFormat.format(totalAmount) + "원";
    }



}


