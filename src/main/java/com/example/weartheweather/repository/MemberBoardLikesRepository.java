package com.example.weartheweather.repository;

import com.example.weartheweather.dto.MemberBoardLikesDTO;
import com.example.weartheweather.entity.MemberBoardEntity;
import com.example.weartheweather.entity.MemberBoardLikesEntity;
import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberBoardLikesRepository extends JpaRepository<MemberBoardLikesEntity, Long> {

    @Query(value = "select count(b) FROM MemberBoardLikesEntity b where b.memberBoardEntity =:memberBoardEntity")
    int countBoardLikes(MemberBoardEntity memberBoardEntity);


    

    void deleteByMemberBoardEntityAndMemberEntity(MemberBoardEntity memberBoardEntity, MemberEntity memberEntity);

    MemberBoardLikesEntity findByMemberBoardEntityAndMemberEntity(MemberBoardEntity memberBoardEntity, MemberEntity memberEntity);



    List<MemberBoardLikesEntity> findByCreatedAtBetween(LocalDateTime lastWeek, LocalDateTime today);



    @Query(value = "select count(b) FROM MemberBoardLikesEntity b where b.writerEntity =:memberEntity")
    Long countByWriterEntity(MemberEntity memberEntity);
}
