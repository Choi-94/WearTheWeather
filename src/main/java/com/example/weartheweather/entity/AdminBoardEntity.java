package com.example.weartheweather.entity;

import com.example.weartheweather.dto.AdminBoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//관리자 작성테이블  DTO엔 String 타입의 createdAt(작성일) 변수 있어야함, 단일파일 받는것도!
@Entity
@Setter
@Getter
@Table(name = "admin_board_table")
public class AdminBoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    private String hashTags;
    @Column
    private String season;
    @Column
    private String weather;
    @Column
    private String temp;
    @Column
    private int height;
    @Column
    private String gender;
    @Column
    private int boardHits;
    @Column
    private int boardLikes;

    //상의
    @Column
    private String top;
    //상의 설명
    @Column
    private String topDetail;
    //상의 가격
    @Column
    private Long topPrice;
    @Column
    private String bottom;
    @Column
    private String bottomDetail;
    @Column
    private Long bottomPrice;
    @Column
    private String etc;
    @Column
    private String etcDetail;
    @Column
    private Long etcPrice;
    @OneToMany(mappedBy = "adminBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AdminBoardFileEntity> adminBoardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "adminBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AdminBoardLikesEntity> adminBoardLikesEntityList = new ArrayList<>();
    public static AdminBoardEntity toSaveEntity(AdminBoardDTO adminBoardDTO) {
        AdminBoardEntity adminBoardEntity = new AdminBoardEntity();
        adminBoardEntity.setHashTags(adminBoardDTO.getHashTags());
        adminBoardEntity.setSeason(adminBoardDTO.getSeason());
        adminBoardEntity.setWeather(adminBoardDTO.getWeather());
        adminBoardEntity.setTemp(adminBoardDTO.getTemp());
        adminBoardEntity.setHeight(adminBoardDTO.getHeight());
        adminBoardEntity.setGender(adminBoardDTO.getGender());
        adminBoardEntity.setBoardHits(0);
        adminBoardEntity.setBoardLikes(0);
        adminBoardEntity.setTop(adminBoardDTO.getTop());
        adminBoardEntity.setTopDetail(adminBoardDTO.getTopDetail());
        adminBoardEntity.setTopPrice(adminBoardDTO.getTopPrice());
        adminBoardEntity.setBottom(adminBoardDTO.getBottom());
        adminBoardEntity.setBottomDetail(adminBoardDTO.getBottomDetail());
        adminBoardEntity.setBottomPrice(adminBoardDTO.getBottomPrice());
        adminBoardEntity.setEtc(adminBoardDTO.getEtc());
        adminBoardEntity.setEtcDetail(adminBoardDTO.getEtcDetail());
        adminBoardEntity.setEtcPrice(adminBoardDTO.getEtcPrice());
        return adminBoardEntity;
    }

    public static AdminBoardEntity toUpdateEntity(AdminBoardDTO adminBoardDTO) {
        AdminBoardEntity adminBoardEntity = new AdminBoardEntity();
        adminBoardEntity.setId(adminBoardDTO.getId());
        adminBoardEntity.setHashTags(adminBoardDTO.getHashTags());
        adminBoardEntity.setSeason(adminBoardDTO.getSeason());
        adminBoardEntity.setWeather(adminBoardDTO.getWeather());
        adminBoardEntity.setTemp(adminBoardDTO.getTemp());
        adminBoardEntity.setHeight(adminBoardDTO.getHeight());
        adminBoardEntity.setGender(adminBoardDTO.getGender());
        adminBoardEntity.setBoardHits(adminBoardDTO.getBoardHits());
        adminBoardEntity.setBoardLikes(adminBoardDTO.getBoardLikes());
        adminBoardEntity.setTop(adminBoardDTO.getTop());
        adminBoardEntity.setTopDetail(adminBoardDTO.getTopDetail());
        adminBoardEntity.setTopPrice(adminBoardDTO.getTopPrice());
        adminBoardEntity.setBottom(adminBoardDTO.getBottom());
        adminBoardEntity.setBottomDetail(adminBoardDTO.getBottomDetail());
        adminBoardEntity.setBottomPrice(adminBoardDTO.getBottomPrice());
        adminBoardEntity.setEtc(adminBoardDTO.getEtc());
        adminBoardEntity.setEtcDetail(adminBoardDTO.getEtcDetail());
        adminBoardEntity.setEtcPrice(adminBoardDTO.getEtcPrice());
        return adminBoardEntity;
    }
}
