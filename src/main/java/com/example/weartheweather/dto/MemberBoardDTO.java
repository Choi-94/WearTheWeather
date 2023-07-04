package com.example.weartheweather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class MemberBoardDTO {
    private Long id;
    private String season;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private int boardLikes;
    private int boardHits;
    private List<MultipartFile> boardFile;
    private String originalFileName;
    private String storedFileName;
    private String createdAt;

}
