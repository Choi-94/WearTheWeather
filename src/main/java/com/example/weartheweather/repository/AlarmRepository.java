package com.example.weartheweather.repository;

import com.example.weartheweather.entity.AlarmEntity;
import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository  extends JpaRepository<AlarmEntity,Long> {

    List<AlarmEntity> findByWriterMemberEntity(MemberEntity loginMemberEntity);
}