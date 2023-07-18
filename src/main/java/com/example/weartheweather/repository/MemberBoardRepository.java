package com.example.weartheweather.repository;

import com.example.weartheweather.entity.MemberBoardEntity;
import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface
MemberBoardRepository extends JpaRepository<MemberBoardEntity,Long> {
    @Modifying
    @Query(value = "update MemberBoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);


    @Modifying
    @Query(value = "update MemberBoardEntity b set b.boardLikes=b.boardLikes+1 where b.id=:id")
    void addBoardLikes(Long id);

    @Modifying
    @Query(value = "update MemberBoardEntity b set b.boardLikes=b.boardLikes-1 where b.id=:id")
    void deleteBoardLikes(Long id);




    List<MemberBoardEntity> findByMemberEntity(MemberEntity memberEntity);



}
