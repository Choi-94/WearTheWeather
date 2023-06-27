package com.example.weartheweather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlarmDTO {
    private Long id;
    private Long writerId;
    private Long loginId;
    private String message;
    private Long boardId;
    private Long productId;
    private String createdAt;
}
