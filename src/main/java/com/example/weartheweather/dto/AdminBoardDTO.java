package com.example.weartheweather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class AdminBoardDTO {
    private Long id;
    private String hashTags;
    private String season;
    private String weather;
    private int height;
    private String gender;
    private int boardHits;
    private int boardLikes;
    private String temp;
    private String top;
    private String topDetail;
    private Long topPrice;
    private String bottom;
    private String bottomDetail;
    private Long bottomPrice;
    private String shoes;
    private String shoesDetail;
    private Long shoesPrice;
    private String createdAt;
    private MultipartFile boardFile;
}
