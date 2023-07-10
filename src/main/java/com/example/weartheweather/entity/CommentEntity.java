package com.example.weartheweather.entity;

import com.example.weartheweather.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String commentContents;
    @Column(length = 50)
    private String commentWriter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private MemberBoardEntity memberBoardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private MemberEntity memberEntity;

    public static CommentEntity toSaveEntity(MemberEntity memberEntity, MemberBoardEntity memberBoardEntity, CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setMemberBoardEntity(memberBoardEntity);
        commentEntity.setMemberEntity(memberEntity);
        return commentEntity;
    }
}
