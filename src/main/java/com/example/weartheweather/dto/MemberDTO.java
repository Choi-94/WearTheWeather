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

    public static MemberDTO toUpdatePay(MemberEntity memberEntity, Long memberWeatherPay){
        MemberDTO memberDTO = new MemberDTO();
        Long WeatherPay = memberEntity.getMemberWeatherPay();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberGender(memberEntity.getMemberGender());
        memberDTO.setMemberWeatherPay(WeatherPay+memberWeatherPay);
        memberDTO.setMemberNickName(memberEntity.getMemberNickName());
        memberDTO.setMemberPoints(memberEntity.getMemberPoints());
        memberDTO.setPlatform(memberEntity.getPlatform());
        return memberDTO;
    }

    public static MemberDTO updatePay(MemberEntity memberEntity,Long totalAmount){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberGender(memberEntity.getMemberGender());
        memberDTO.setMemberWeatherPay(memberEntity.getMemberWeatherPay()+totalAmount);
        memberDTO.setMemberNickName(memberEntity.getMemberNickName());
        memberDTO.setMemberPoints(memberEntity.getMemberPoints());
        memberDTO.setPlatform(memberEntity.getPlatform());
        return memberDTO;
    }
    public static MemberDTO updatePayConfirm(MemberEntity memberEntity,Long ProductPrice){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberGender(memberEntity.getMemberGender());
        memberDTO.setMemberWeatherPay(memberEntity.getMemberWeatherPay()-ProductPrice);
        memberDTO.setMemberNickName(memberEntity.getMemberNickName());
        memberDTO.setMemberPoints(memberEntity.getMemberPoints());
        memberDTO.setPlatform(memberEntity.getPlatform());
        return memberDTO;
    }

    public static MemberDTO updateSellerPay(MemberEntity memberEntity,Long ProductPrice){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberGender(memberEntity.getMemberGender());
        memberDTO.setMemberWeatherPay(memberEntity.getMemberWeatherPay()+ProductPrice);
        memberDTO.setMemberNickName(memberEntity.getMemberNickName());
        memberDTO.setMemberPoints(memberEntity.getMemberPoints());
        memberDTO.setPlatform(memberEntity.getPlatform());
        return memberDTO;
    }

}
