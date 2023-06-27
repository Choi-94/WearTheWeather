package com.example.weartheweather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecentKeywordsDTO {
    private Long id;
    private String keyword;
    private String createdAt;
    private Long memberId;

}
