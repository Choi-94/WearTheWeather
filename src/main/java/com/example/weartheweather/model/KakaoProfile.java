
package com.example.weartheweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Generated;
import java.util.Properties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {

    public Long id;
    public KakaoAccount kakao_account;
    public Properties properties;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {
        public String email;
        public String gender;
        public String nickname;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Properties {

        public String nickname;

    }
}