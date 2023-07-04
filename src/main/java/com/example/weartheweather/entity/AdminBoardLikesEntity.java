package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Member;

@Entity
@Setter
@Getter
@Table(name = "admin_likes_table")
public class AdminBoardLikesEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //멤버테이블과 조인(멤버 엔티티에 @OneToMany로 연결해주세요~)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private AdminBoardEntity adminBoardEntity;

    public static AdminBoardLikesEntity toSaveEntity(MemberEntity memberEntity, AdminBoardEntity adminBoardEntity) {
        AdminBoardLikesEntity adminBoardLikesEntity = new AdminBoardLikesEntity();
        adminBoardLikesEntity.setMemberEntity(memberEntity);
        adminBoardLikesEntity.setAdminBoardEntity(adminBoardEntity);
        return adminBoardLikesEntity;
    }
}
