package com.example.weartheweather.entity;

import com.example.weartheweather.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String memberEmail;

    @Column(length = 40)
    private String memberPassword;

    @Column(length = 20, nullable = false, unique = true)
    private String memberNickName;


    @Column(nullable = false)
    private String memberGender;

    @Column
    private Long memberWeatherPay;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberGradeEntity> memberGradeEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "writerMemberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<AlarmEntity> alarmEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "writer_id", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberBoardEntity> memberBoardEntityList = new ArrayList<>();
    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberNickName(memberDTO.getMemberNickName());
        memberEntity.setMemberGender(memberDTO.getMemberGender());
        return memberEntity;
    }
}
