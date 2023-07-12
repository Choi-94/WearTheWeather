package com.example.weartheweather.repository;


import com.example.weartheweather.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {


    Optional<MemberEntity> findByMemberNickName(String memberNickName);

    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);

    Optional<MemberEntity> findByMemberEmail(String memberEmail);

    @Modifying
    @Query("update MemberEntity m set m.memberWeatherPay = :balance where m.id = :id")
    void updateMemberWeatherPay(@Param("id") Long id, @Param("balance") Long balance);
}
