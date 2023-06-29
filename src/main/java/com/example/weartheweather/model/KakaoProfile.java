
package com.example.weartheweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.annotation.Generated;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Generated("jsonschema2pojo")
public class KakaoProfile {

    public Long id;
    public String connectedAt;
    public KakaoAccount kakaoAccount;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Generated("jsonschema2pojo")
    public class KakaoAccount {
        public String email;
        public String gender;
    }
}