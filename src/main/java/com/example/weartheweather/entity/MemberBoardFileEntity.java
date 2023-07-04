package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "member_board_file_table")
public class MemberBoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String originalFileName;

    @Column(length = 50)
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberBoard_id")
    private MemberBoardEntity memberBoardEntity;

    public static MemberBoardFileEntity toSaveBoardFileEntity(MemberBoardEntity savedEntity, String originalFileName, String storedFileName) {
        MemberBoardFileEntity memberBoardFileEntity = new MemberBoardFileEntity();
        memberBoardFileEntity.setMemberBoardEntity(savedEntity);
        memberBoardFileEntity.setOriginalFileName(originalFileName);
        memberBoardFileEntity.setStoredFileName(storedFileName);
        return memberBoardFileEntity;
    }

}
