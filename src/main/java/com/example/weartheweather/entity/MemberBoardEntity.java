package com.example.weartheweather.entity;

import com.example.weartheweather.dto.MemberBoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//사용자게시글  DTO엔 String 타입의 createdAt(작성일) 변수 있어야함, 다중파일 받는것도!!
@Entity
@Getter
@Setter
@Table(name = "member_board_table")
public class MemberBoardEntity extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String season;
    @Column(length = 20)
    private String boardWriter;
    @Column(length = 50)
    private String boardTitle;
    @Column(columnDefinition = "TEXT")
    private String boardContents;
    @Column(length = 50)
    private String lookStyle;
    @Column
    private int boardLikes;
    @Column
    private int boardHits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "memberBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AlarmEntity> alarmEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "memberBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberBoardFileEntity> memberBoardFileEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "memberBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberBoardLikesEntity> likesEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "memberBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardRankingEntity> boardRankingEntityList = new ArrayList<>();

    public static MemberBoardEntity toSaveEntity(MemberBoardDTO memberBoardDTO, MemberEntity memberEntity) {
        MemberBoardEntity memberBoardEntity = new MemberBoardEntity();
        memberBoardEntity.setMemberEntity(memberEntity);
        memberBoardEntity.setSeason(memberBoardDTO.getSeason());
        memberBoardEntity.setBoardWriter(memberEntity.getMemberNickName());
        memberBoardEntity.setBoardTitle(memberBoardDTO.getBoardTitle());
        memberBoardEntity.setBoardContents(memberBoardDTO.getBoardContents());
        memberBoardEntity.setLookStyle(memberBoardDTO.getLookStyle());
        memberBoardEntity.setBoardLikes(0);
        memberBoardEntity.setBoardHits(0);
        return memberBoardEntity;
    }

    public static MemberBoardEntity toUpdateEntity(MemberBoardDTO memberBoardDTO, MemberEntity memberEntity) {
        MemberBoardEntity memberBoardEntity = new MemberBoardEntity();
        memberBoardEntity.setId(memberBoardDTO.getId());
        memberBoardEntity.setMemberEntity(memberEntity);
        memberBoardEntity.setSeason(memberBoardDTO.getSeason());
        memberBoardEntity.setBoardWriter(memberBoardDTO.getBoardWriter());
        memberBoardEntity.setBoardTitle(memberBoardDTO.getBoardTitle());
        memberBoardEntity.setBoardContents(memberBoardDTO.getBoardContents());
        memberBoardEntity.setLookStyle(memberBoardDTO.getLookStyle());
        memberBoardEntity.setBoardLikes(memberBoardDTO.getBoardLikes());
        memberBoardEntity.setBoardHits(memberBoardDTO.getBoardHits());
        return memberBoardEntity;
    }

}
