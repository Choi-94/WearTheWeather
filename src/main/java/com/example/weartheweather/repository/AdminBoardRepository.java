package com.example.weartheweather.repository;

import com.example.weartheweather.entity.AdminBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminBoardRepository extends JpaRepository<AdminBoardEntity, Long> {

    @Modifying
    @Query(value = "update AdminBoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);





}
