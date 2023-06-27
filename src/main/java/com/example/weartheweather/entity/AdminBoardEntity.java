package com.example.weartheweather.entity;

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
public class AdminBoardEntity {
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
    private int height;
    @Column
    private String gender;
    @Column
    private int boardHits;
    @Column
    private int boardLikes;
    @Column
    private String temp;
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
    private String shoes;
    @Column
    private String shoesDetail;
    @Column
    private Long shoesPrice;
    @OneToMany(mappedBy = "adminBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AdminBoardFileEntity> adminBoardFileEntityList = new ArrayList<>();

}
