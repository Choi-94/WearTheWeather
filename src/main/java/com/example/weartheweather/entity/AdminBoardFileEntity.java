package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "admin_board_file_table")
public class AdminBoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String originalFileName;

    @Column(length = 50)
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminBoard_id")
    private AdminBoardEntity adminBoardEntity;

    public static AdminBoardFileEntity toSaveBoardFileEntity(AdminBoardEntity savedEntity, String originalFileName, String storedFileName) {
        AdminBoardFileEntity adminBoardFileEntity = new AdminBoardFileEntity();
        adminBoardFileEntity.setAdminBoardEntity(savedEntity);
        adminBoardFileEntity.setOriginalFileName(originalFileName);
        adminBoardFileEntity.setStoredFileName(storedFileName);
        return adminBoardFileEntity;
    }
}
