package com.example.weartheweather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MarketProductDTO {
    private Long id;
    private String productSize;
    private String productTitle;
    private String transactionArea;
    private int productPrice;;
    private String productContents;
    private String productSeason;
    private String productWeather;
    private String temp;
    private String hashTag;
    private int productDibs;
    private int productHits;
    private String createdAt;

    private List<MultipartFile> productFile;
    private int fileAttached;
    private List<String> originalFileName = new ArrayList<>();
    private List<String> storedFileName = new ArrayList<>();
}