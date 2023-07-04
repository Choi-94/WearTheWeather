package com.example.weartheweather.repository;

import com.example.weartheweather.entity.MemberBoardEntity;
import com.example.weartheweather.entity.MemberBoardLikesEntity;
import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberBoardLikesRepository extends JpaRepository<MemberBoardLikesEntity, Long> {

    @Query(value = "select count(b) FROM MemberBoardLikesEntity b where b.memberBoardEntity =:memberBoardEntity")
    int countBoardLikes(MemberBoardEntity memberBoardEntity);

    Optional<MemberBoardLikesEntity> findByMemberBoardEntityAndMemberEntity(Optional<MemberBoardEntity> memberBoardEntity, Optional<MemberEntity> memberEntity);
}
