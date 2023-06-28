package com.example.weartheweather.dto;


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


}
