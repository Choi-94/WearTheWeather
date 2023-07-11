package com.example.weartheweather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlarmDTO {
    private Long id;
    private String message;
    private String type;
    private int isRead;
    private Long writerId;
    private Long loginId;
    private Long boardId;
    private Long productId;
    private String createdAt;
}
