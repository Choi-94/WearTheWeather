package com.example.weartheweather.repository;


import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.entity.AdminBoardLikesEntity;
import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminBoardLikesRepository extends JpaRepository<AdminBoardLikesEntity, Long> {



    @Query(value = "select count(b) FROM AdminBoardLikesEntity b where b.adminBoardEntity.id =:id")
    int countBoardLikes(Long id);


    Optional<AdminBoardLikesEntity> findByAdminBoardEntityAndMemberEntity(AdminBoardEntity adminBoardEntity, MemberEntity memberEntity);
}
