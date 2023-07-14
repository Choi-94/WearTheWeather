package com.example.weartheweather.repository;

import com.example.weartheweather.entity.AlarmEntity;
import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRepository  extends JpaRepository<AlarmEntity,Long> {

    @Query("select a from AlarmEntity a where a.isReadFlag = 1 and a.writerMemberEntity = :loginMemberEntity")
    List<AlarmEntity> findByWriterMemberEntity(@Param("loginMemberEntity") MemberEntity loginMemberEntity);

    @Modifying
    @Query(value = "update AlarmEntity a set a.isReadFlag = 0 where a.id=:id")
    void updateIsReadFlag(@Param("id") Long id);
}