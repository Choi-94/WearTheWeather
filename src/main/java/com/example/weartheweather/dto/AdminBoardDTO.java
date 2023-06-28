package com.example.weartheweather.dto;

import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

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
}
