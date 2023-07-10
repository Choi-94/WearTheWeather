package com.example.weartheweather.repository;

import com.example.weartheweather.entity.CommentEntity;
import com.example.weartheweather.entity.MemberBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByMemberBoardEntityOrderByIdDesc(MemberBoardEntity memberBoardEntity);



    void deleteByIdAndMemberBoardEntityId(Long id, Long boardId);
}
