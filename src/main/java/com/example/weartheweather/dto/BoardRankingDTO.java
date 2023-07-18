package com.example.weartheweather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardRankingDTO {
    Long id;
    int likeCount;
    Long boardId;
}
