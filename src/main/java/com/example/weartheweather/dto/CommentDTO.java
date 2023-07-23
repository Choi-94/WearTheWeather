package com.example.weartheweather.dto;

import com.example.weartheweather.entity.CommentEntity;
import com.example.weartheweather.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;
    private String commentContents;
    private String commentWriter;
    private Long boardId;
    private Long writerId;
    private String createdAt;
    private String calculateElapsedDays;

    private int memberPoints;

    public static CommentDTO toDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentWriter(commentEntity.getMemberEntity().getMemberNickName());
        commentDTO.setBoardId(commentEntity.getMemberBoardEntity().getId());
        commentDTO.setWriterId(commentEntity.getMemberEntity().getId());
        commentDTO.setCreatedAt(UtilClass.dateFormat(commentEntity.getCreatedAt()));
        commentDTO.setCalculateElapsedDays(commentEntity.calculateElapsedDays());

        return commentDTO;
    }

    public static CommentDTO toDTOpoint(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentWriter(commentEntity.getMemberEntity().getMemberNickName());
        commentDTO.setBoardId(commentEntity.getMemberBoardEntity().getId());
        commentDTO.setWriterId(commentEntity.getMemberEntity().getId());
        commentDTO.setCreatedAt(UtilClass.dateFormat(commentEntity.getCreatedAt()));
        commentDTO.setMemberPoints(commentEntity.getMemberEntity().getMemberPoints());
        commentDTO.setCalculateElapsedDays(commentEntity.calculateElapsedDays());
        return commentDTO;
    }
}
