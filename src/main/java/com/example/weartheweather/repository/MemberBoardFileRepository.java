package com.example.weartheweather.repository;

import com.example.weartheweather.entity.MemberBoardEntity;
import com.example.weartheweather.entity.MemberBoardFileEntity;
import com.example.weartheweather.entity.MemberBoardLikesEntity;
import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberBoardFileRepository extends JpaRepository<MemberBoardFileEntity, Long> {


}
