package com.example.weartheweather.dto;

import com.example.weartheweather.entity.AdminBoardLikesEntity;
import com.example.weartheweather.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminBoardLikesDTO extends BaseEntity {

    private Long id;
    private Long memberId;
    private Long boardId;

    public static  AdminBoardLikesDTO toDTO(AdminBoardLikesEntity adminBoardLikesEntity) {
        AdminBoardLikesDTO adminBoardLikesDTO = new AdminBoardLikesDTO();
        adminBoardLikesDTO.setId(adminBoardLikesEntity.getId());
        adminBoardLikesDTO.setMemberId(adminBoardLikesEntity.getMemberEntity().getId());
        adminBoardLikesDTO.setBoardId(adminBoardLikesEntity.getAdminBoardEntity().getId());
        return adminBoardLikesDTO;
    }
}
