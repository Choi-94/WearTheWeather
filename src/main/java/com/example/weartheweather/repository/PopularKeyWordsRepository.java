package com.example.weartheweather.repository;

import com.example.weartheweather.entity.PopularKeywordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularKeyWordsRepository extends JpaRepository<PopularKeywordsEntity, Long> {
}
