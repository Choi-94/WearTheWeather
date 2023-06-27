package com.example.weartheweather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberBoardLikesDTO {
    private Long id;
    private Long memberId;
    private Long boardId;
    private String createdAt;
}
