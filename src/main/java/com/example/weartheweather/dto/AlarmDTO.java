package com.example.weartheweather.dto;

import com.example.weartheweather.entity.AlarmEntity;
import com.example.weartheweather.util.UtilClass;
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

    public static AlarmDTO toDTO(AlarmEntity alarmEntity) {
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setId(alarmEntity.getId());
        alarmDTO.setMessage(alarmEntity.getMessage());
        alarmDTO.setType(alarmEntity.getType());
        alarmDTO.setIsRead(alarmEntity.getIsRead());
        alarmDTO.setWriterId(alarmEntity.getWriterMemberEntity().getId());
        alarmDTO.setLoginId(alarmEntity.getLoginMemberEntity().getId());
        alarmDTO.setBoardId(alarmEntity.getMemberBoardEntity().getId());
        alarmDTO.setProductId(null);
        alarmDTO.setCreatedAt(UtilClass.dateFormat(alarmEntity.getCreatedAt()));
        return alarmDTO;
    }
}