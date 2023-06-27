package com.example.weartheweather.repository;

import com.example.weartheweather.entity.MemberBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBoardRepository extends JpaRepository<MemberBoardEntity,Long> {
}
