package com.example.weartheweather.dto;


import com.example.weartheweather.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter@Setter@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberNickName;
    private String memberGender;
    private String memberPassword;
    private Long memberWeatherPay;
    private int memberPoints;
    private String platform;

    public static MemberDTO tofindAll(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberGender(memberEntity.getMemberGender());
        memberDTO.setMemberWeatherPay(memberEntity.getMemberWeatherPay());
        memberDTO.setMemberNickName(memberEntity.getMemberNickName());
        memberDTO.setMemberPoints(memberEntity.getMemberPoints());
        memberDTO.setPlatform(memberEntity.getPlatform());
        return memberDTO;
    }


}
