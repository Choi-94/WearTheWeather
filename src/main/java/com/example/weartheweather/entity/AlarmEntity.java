package com.example.weartheweather.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//알림테이블 DTO엔 String 타입의 createdAt(작성일) 변수 있어야함
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "alarm_table")
public class AlarmEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String message;
    @Column(length = 50)
    private String type;
    @Column
    private int isReadFlag;
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


    public static AlarmEntity LikesToSaveEntity(MemberEntity writerMemberEntity, MemberEntity loginMemberEntity, MemberBoardEntity memberBoardEntity, String type) {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setIsReadFlag(1);
        alarmEntity.setLoginMemberEntity(loginMemberEntity);
        alarmEntity.setWriterMemberEntity(writerMemberEntity);
        alarmEntity.setMemberBoardEntity(memberBoardEntity);
        alarmEntity.setMessage(loginMemberEntity.getMemberNickName() + "님이 게시글에 좋아요를 눌렀습니다.");
        alarmEntity.setMarketProductEntity(null);
        alarmEntity.setType(type);
        return alarmEntity;
    }

    public static AlarmEntity buysToSaveEntity(MemberEntity writerMemberEntity, MemberEntity loginMemberEntity, MarketProductEntity marketProductEntity, String type) {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setIsReadFlag(1);
        alarmEntity.setLoginMemberEntity(loginMemberEntity);
        alarmEntity.setWriterMemberEntity(writerMemberEntity);
        alarmEntity.setMemberBoardEntity(null);
        alarmEntity.setType(type);
        alarmEntity.setMessage("등록한 상품을 " + loginMemberEntity.getMemberNickName() + "님이 배송요청했습니다.");
        alarmEntity.setMarketProductEntity(marketProductEntity);
        return alarmEntity;
    }

    public static AlarmEntity commentToSaveEntity(MemberEntity writerMemberEntity, MemberEntity loginMemberEntity, MemberBoardEntity memberBoardEntity, String type) {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setIsReadFlag(1);
        alarmEntity.setLoginMemberEntity(loginMemberEntity);
        alarmEntity.setWriterMemberEntity(writerMemberEntity);
        alarmEntity.setMemberBoardEntity(memberBoardEntity);
        alarmEntity.setMessage(loginMemberEntity.getMemberNickName() + "님이 게시글에 댓글을 달았습니다.");
        alarmEntity.setMarketProductEntity(null);
        alarmEntity.setType(type);
        return alarmEntity;
    }

    public static AlarmEntity buyConfirmToSaveEntity(MemberEntity writerMemberEntity, MemberEntity loginMemberEntity, MarketProductEntity marketProductEntity, String type) {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setIsReadFlag(1);
        alarmEntity.setLoginMemberEntity(loginMemberEntity);
        alarmEntity.setWriterMemberEntity(writerMemberEntity);
        alarmEntity.setMemberBoardEntity(null);
        alarmEntity.setType(type);
        alarmEntity.setMessage("등록한 상품을 " + loginMemberEntity.getMemberNickName() + "님이 구매확정했습니다.");
        alarmEntity.setMarketProductEntity(marketProductEntity);
        return alarmEntity;
    }
}
