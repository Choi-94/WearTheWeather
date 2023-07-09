package com.example.weartheweather.repository;

import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.entity.MemberBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberBoardRepository extends JpaRepository<MemberBoardEntity,Long> {
    @Modifying
    @Query(value = "update MemberBoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

    Page<MemberBoardEntity> findByBoardWriterContaining(String q, Pageable pageable);
    Page<MemberBoardEntity> findByBoardTitleContaining(String q, Pageable pageable);

    @Modifying
    @Query(value = "update MemberBoardEntity b set b.boardLikes=b.boardLikes+1 where b.id=:id")
    void addBoardLikes(Long id);

    @Modifying
    @Query(value = "update MemberBoardEntity b set b.boardLikes=b.boardLikes-1 where b.id=:id")
    void deleteBoardLikes(Long id);
}
