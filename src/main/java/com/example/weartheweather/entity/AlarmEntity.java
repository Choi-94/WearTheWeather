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
public class AlarmEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String message;

    @Column(length = 50)
    private String type;
    @Column
    private int isRead;

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


    public static AlarmEntity toSaveEntity(MemberEntity writerMemberEntity, MemberEntity loginMemberEntity, MemberBoardEntity memberBoardEntity, String type) {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setIsRead(1);
        alarmEntity.setLoginMemberEntity(loginMemberEntity);
        alarmEntity.setWriterMemberEntity(writerMemberEntity);
        alarmEntity.setMemberBoardEntity(memberBoardEntity);
        alarmEntity.setMarketProductEntity(null);
        alarmEntity.setType(type);

        if (type == "Likes") {
            alarmEntity.setMessage(loginMemberEntity.getMemberNickName() + "님이 " + memberBoardEntity.getBoardTitle() + "게시글에 좋아요를 눌렀습니다.");
        } else if (type == "comments") {
            alarmEntity.setMessage(loginMemberEntity.getMemberNickName() + "님이 " + memberBoardEntity.getBoardTitle() + "게시글에 댓글을 작성했습니다.");
        } else {
            alarmEntity.setMessage("냥야냐ㅑ양");
        }
        return alarmEntity;
    }
}
