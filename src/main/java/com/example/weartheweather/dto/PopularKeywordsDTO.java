package com.example.weartheweather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PopularKeywordsDTO {
    private Long id;
    private String keyword;
    private String genderw;
    private String genderm;
    private Long count;
    private int tall;
}
