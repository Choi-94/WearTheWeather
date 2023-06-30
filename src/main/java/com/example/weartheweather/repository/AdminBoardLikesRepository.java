package com.example.weartheweather.repository;

import com.example.weartheweather.entity.AdminBoardLikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdminBoardLikesRepository extends JpaRepository<AdminBoardLikesEntity, Long> {



    @Query(value = "select count(b) FROM AdminBoardLikesEntity b where b.adminBoardEntity.id =:id")
    int countBoardLikes(Long id);
}
