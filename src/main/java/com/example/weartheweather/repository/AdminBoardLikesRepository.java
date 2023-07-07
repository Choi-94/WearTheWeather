package com.example.weartheweather.repository;


import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.entity.AdminBoardLikesEntity;
import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminBoardLikesRepository extends JpaRepository<AdminBoardLikesEntity, Long> {


    Optional<AdminBoardLikesEntity> findByAdminBoardEntityAndMemberEntity(Optional<AdminBoardEntity> adminBoardEntity, Optional<MemberEntity> memberEntity);
    @Query(value = "select count(b) FROM AdminBoardLikesEntity b where b.adminBoardEntity =:adminBoardEntity")
    int countBoardLikes(AdminBoardEntity adminBoardEntity);


    void deleteByAdminBoardEntityAndMemberEntity(Optional<AdminBoardEntity> adminBoardEntity, Optional<MemberEntity> memberEntity);


    List<AdminBoardLikesEntity> findByMemberEntity(MemberEntity memberEntity);
}
