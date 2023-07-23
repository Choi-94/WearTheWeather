package com.example.weartheweather.dto;

import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.entity.AdminBoardLikesEntity;
import com.example.weartheweather.entity.BaseEntity;
import com.example.weartheweather.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminBoardLikesDTO{

    private Long id;
    private Long memberId;
    private Long boardId;
    private String createdAt;

    public static  AdminBoardLikesDTO toDTO(AdminBoardLikesEntity adminBoardLikesEntity) {
        AdminBoardLikesDTO adminBoardLikesDTO = new AdminBoardLikesDTO();
        adminBoardLikesDTO.setId(adminBoardLikesEntity.getId());
        adminBoardLikesDTO.setMemberId(adminBoardLikesEntity.getMemberEntity().getId());
        adminBoardLikesDTO.setBoardId(adminBoardLikesEntity.getAdminBoardEntity().getId());
        adminBoardLikesDTO.setCreatedAt(UtilClass.dateFormat(adminBoardLikesEntity.getCreatedAt()));
        return adminBoardLikesDTO;
    }
}
