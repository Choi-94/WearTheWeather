package com.example.weartheweather.dto;


import com.example.weartheweather.entity.MemberBoardEntity;
import com.example.weartheweather.entity.MemberBoardFileEntity;

import com.example.weartheweather.util.UtilClass;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberBoardDTO {
    private Long id;
    private Long writerId;

    private String season;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private String lookStyle;
    private int boardLikes;
    private int boardHits;
    private int memberPoints;
    private List<MultipartFile> boardFile;
    private List<String> originalFileName = new ArrayList<>();
    private List<String> storedFileName = new ArrayList<>();

    private String createdAt;

    public static MemberBoardDTO toDTO(MemberBoardEntity memberBoardEntity) {
        MemberBoardDTO memberBoardDTO = new MemberBoardDTO();
        memberBoardDTO.setId(memberBoardEntity.getId());
        memberBoardDTO.setWriterId(memberBoardEntity.getMemberEntity().getId());
        memberBoardDTO.setSeason(memberBoardEntity.getSeason());
        memberBoardDTO.setBoardWriter(memberBoardEntity.getBoardWriter());
        memberBoardDTO.setBoardTitle(memberBoardEntity.getBoardTitle());
        memberBoardDTO.setBoardContents(memberBoardEntity.getBoardContents());
        memberBoardDTO.setLookStyle(memberBoardEntity.getLookStyle());
        memberBoardDTO.setBoardLikes(memberBoardEntity.getBoardLikes());
        memberBoardDTO.setMemberPoints(memberBoardEntity.getMemberEntity().getMemberPoints());
        memberBoardDTO.setBoardHits(memberBoardEntity.getBoardHits());
        memberBoardDTO.setCreatedAt(UtilClass.dateFormat(memberBoardEntity.getCreatedAt()));

        List<String> originalFileNameList = new ArrayList<>();
        List<String> storedFileNameList = new ArrayList<>();

        for (MemberBoardFileEntity boardFileEntity : memberBoardEntity.getMemberBoardFileEntityList()) {
            originalFileNameList.add(boardFileEntity.getOriginalFileName());
            storedFileNameList.add(boardFileEntity.getStoredFileName());
        }
        memberBoardDTO.setOriginalFileName(originalFileNameList);
        memberBoardDTO.setStoredFileName(storedFileNameList);
        return memberBoardDTO;
    }


}
