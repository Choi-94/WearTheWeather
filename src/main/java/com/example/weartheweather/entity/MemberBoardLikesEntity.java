package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "member_likes_table")
public class MemberBoardLikesEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long likeCount;


    //멤버테이블과 조인(멤버 엔티티에 @OneToMany로 연결해주세요~)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private MemberBoardEntity memberBoardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private MemberEntity writerEntity;

    public static MemberBoardLikesEntity toSaveEntity(MemberEntity memberEntity, MemberBoardEntity memberBoardEntity) {
        MemberBoardLikesEntity memberBoardLikesEntity = new MemberBoardLikesEntity();
        memberBoardLikesEntity.setMemberEntity(memberEntity);
        memberBoardLikesEntity.setMemberBoardEntity(memberBoardEntity);
        memberBoardLikesEntity.setWriterEntity(memberBoardEntity.getMemberEntity());
        return memberBoardLikesEntity;
    }

}
