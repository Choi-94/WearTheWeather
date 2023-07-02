
package com.example.weartheweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Generated;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {

    public Long id;
    public KakaoAccount kakao_account;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {
        public String email;
        public String gender;
    }
}