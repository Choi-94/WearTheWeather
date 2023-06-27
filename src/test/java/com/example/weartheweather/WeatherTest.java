package com.example.weartheweather;

import com.example.weartheweather.repository.AdminBoardRepository;
import com.example.weartheweather.service.AdminBoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class WeatherTest {
    @Autowired
    private AdminBoardService adminBoardService;
    @Autowired
    private AdminBoardRepository adminBoardRepository;

//    @Test
//    @Transactional
//    @Rollback("DB에 데이터 붓기")
//    @DisplayName("DB에 데이터 붓기")
//    public void adminBoardSaveList() {
//
//    }
}
