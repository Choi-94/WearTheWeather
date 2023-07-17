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
    private Long count;
    private int tall;
}
