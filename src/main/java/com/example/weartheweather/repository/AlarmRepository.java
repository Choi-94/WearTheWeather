package com.example.weartheweather.repository;

import com.example.weartheweather.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository  extends JpaRepository<AlarmEntity,Long> {
}
