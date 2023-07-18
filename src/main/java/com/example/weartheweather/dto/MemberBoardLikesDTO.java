package com.example.weartheweather.dto;

import com.example.weartheweather.entity.AdminBoardLikesEntity;
import com.example.weartheweather.entity.BaseEntity;
import com.example.weartheweather.entity.MemberBoardLikesEntity;
import com.example.weartheweather.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberBoardLikesDTO{
    private Long id;
    private Long likeCount;
    private Long memberId;
    private Long boardId;
    private String createdAt;

    public static  MemberBoardLikesDTO toDTO(MemberBoardLikesEntity memberBoardLikesEntity) {
        MemberBoardLikesDTO memberBoardLikesDTO = new MemberBoardLikesDTO();
        memberBoardLikesDTO.setId(memberBoardLikesEntity.getId());
        memberBoardLikesDTO.setMemberId(memberBoardLikesEntity.getMemberEntity().getId());
        memberBoardLikesDTO.setBoardId(memberBoardLikesEntity.getMemberBoardEntity().getId());
        memberBoardLikesDTO.setCreatedAt(UtilClass.dateFormat(memberBoardLikesEntity.getCreatedAt()));
        return memberBoardLikesDTO;
    }
}
