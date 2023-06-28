package com.example.weartheweather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;
    private String commentContents;
    private Long boardId;
    private Long writerId;
    private String createdAt;

}
