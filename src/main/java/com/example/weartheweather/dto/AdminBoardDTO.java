package com.example.weartheweather.dto;

import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.text.DecimalFormat;

@Getter
@Setter
@ToString
public class AdminBoardDTO {
    private Long id;
    private String hashTags;
    private String season;
    private String weather;
    private String temp;
    private int height;
    private String gender;
    private int boardHits;
    private int boardLikes;
    private String top;
    private String topDetail;
    private Long topPrice;
    private String bottom;
    private String bottomDetail;
    private Long bottomPrice;
    private String etc;
    private String etcDetail;
    private Long etcPrice;
    private String createdAt;
    private MultipartFile boardFile;
    private String originalFileName;
    private String storedFileName;
    private String totalTags;

    public static AdminBoardDTO toDTO(AdminBoardEntity adminBoardEntity) {
        AdminBoardDTO adminBoardDTO = new AdminBoardDTO();
        adminBoardDTO.setId(adminBoardEntity.getId());
        adminBoardDTO.setHashTags(adminBoardEntity.getHashTags());
        adminBoardDTO.setSeason(adminBoardEntity.getSeason());
        adminBoardDTO.setWeather(adminBoardEntity.getWeather());
        adminBoardDTO.setTemp(adminBoardEntity.getTemp());
        adminBoardDTO.setHeight(adminBoardEntity.getHeight());
        adminBoardDTO.setGender(adminBoardEntity.getGender());
        adminBoardDTO.setBoardHits(adminBoardEntity.getBoardHits());
        adminBoardDTO.setBoardLikes(adminBoardEntity.getBoardLikes());
        adminBoardDTO.setTop(adminBoardEntity.getTop());
        adminBoardDTO.setTopDetail(adminBoardEntity.getTopDetail());
        adminBoardDTO.setTopPrice(adminBoardEntity.getTopPrice());
        adminBoardDTO.setBottom(adminBoardEntity.getBottom());
        adminBoardDTO.setBottomDetail(adminBoardEntity.getBottomDetail());
        adminBoardDTO.setBottomPrice(adminBoardEntity.getBottomPrice());
        adminBoardDTO.setEtc(adminBoardEntity.getEtc());
        adminBoardDTO.setEtcDetail(adminBoardEntity.getEtcDetail());
        adminBoardDTO.setEtcPrice(adminBoardEntity.getEtcPrice());
        adminBoardDTO.setOriginalFileName(adminBoardEntity.getAdminBoardFileEntityList().get(0).getOriginalFileName());
        adminBoardDTO.setStoredFileName(adminBoardEntity.getAdminBoardFileEntityList().get(0).getStoredFileName());
        adminBoardDTO.setCreatedAt(UtilClass.dateFormat(adminBoardEntity.getCreatedAt()));

        return adminBoardDTO;
    }

    public static AdminBoardDTO Search(AdminBoardEntity adminBoardEntity) {
        AdminBoardDTO adminBoardDTO = new AdminBoardDTO();
        adminBoardDTO.setId(adminBoardEntity.getId());
        adminBoardDTO.setHashTags(adminBoardEntity.getHashTags());
        adminBoardDTO.setSeason(adminBoardEntity.getSeason());
        adminBoardDTO.setWeather(adminBoardEntity.getWeather());
        adminBoardDTO.setTemp(adminBoardEntity.getTemp());
        adminBoardDTO.setHeight(adminBoardEntity.getHeight());
        adminBoardDTO.setGender(adminBoardEntity.getGender());
        adminBoardDTO.setBoardHits(adminBoardEntity.getBoardHits());
        adminBoardDTO.setBoardLikes(adminBoardEntity.getBoardLikes());
        adminBoardDTO.setTop(adminBoardEntity.getTop());
        adminBoardDTO.setTopDetail(adminBoardEntity.getTopDetail());
        adminBoardDTO.setTopPrice(adminBoardEntity.getTopPrice());
        adminBoardDTO.setBottom(adminBoardEntity.getBottom());
        adminBoardDTO.setBottomDetail(adminBoardEntity.getBottomDetail());
        adminBoardDTO.setBottomPrice(adminBoardEntity.getBottomPrice());
        adminBoardDTO.setEtc(adminBoardEntity.getEtc());
        adminBoardDTO.setEtcDetail(adminBoardEntity.getEtcDetail());
        adminBoardDTO.setEtcPrice(adminBoardEntity.getEtcPrice());
        adminBoardDTO.setOriginalFileName(adminBoardEntity.getAdminBoardFileEntityList().get(0).getOriginalFileName());
        adminBoardDTO.setStoredFileName(adminBoardEntity.getAdminBoardFileEntityList().get(0).getStoredFileName());
        adminBoardDTO.setCreatedAt(UtilClass.dateFormat(adminBoardEntity.getCreatedAt()));
        adminBoardDTO.setTotalTags(adminBoardEntity.getHashTags()+adminBoardEntity.getSeason()+adminBoardEntity.getBottom()+adminBoardEntity.getWeather()+adminBoardEntity.getTemp()
        +adminBoardEntity.getBottomDetail()+adminBoardEntity.getEtc()+adminBoardEntity.getEtcDetail()+adminBoardEntity.getTop()+adminBoardEntity.getTopDetail());

        return adminBoardDTO;
    }

    public String getFormattedTopPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(this.topPrice) + "원";
    }

    public String getFormattedBottomPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(this.bottomPrice) + "원";
    }

    public String getFormattedETCPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(this.etcPrice) + "원";
    }
}
