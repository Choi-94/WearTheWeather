package com.example.weartheweather.dto;


import com.example.weartheweather.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberNickName;
    private String memberGender;
    private String memberPassword;
    private Long memberWeatherPay;

    public static MemberDTO tofindAll(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberGender(memberEntity.getMemberGender());
        memberDTO.setMemberWeatherPay(memberEntity.getMemberWeatherPay());
        memberDTO.setMemberNickName(memberEntity.getMemberNickName());
        return memberDTO;
    }


}
