package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//알림테이블 DTO엔 String 타입의 createdAt(작성일) 변수 있어야함
@Entity
@Getter
@Setter
@Table(name = "alarm_table")
public class AlarmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length = 50)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private MemberEntity writerMemberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private MemberEntity loginMemberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private MemberBoardEntity memberBoardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private MarketProductEntity marketProductEntity;


}
