package com.example.weartheweather;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.entity.AdminBoardFileEntity;
import com.example.weartheweather.entity.MemberBoardEntity;
import com.example.weartheweather.repository.AdminBoardFileRepository;
import com.example.weartheweather.repository.AdminBoardRepository;
import com.example.weartheweather.service.AdminBoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.LongStream;

@SpringBootTest
public class WeatherTest {
    @Autowired
    private AdminBoardService adminBoardService;
    @Autowired
    private AdminBoardRepository adminBoardRepository;
    @Autowired
    private AdminBoardFileRepository adminBoardFileRepository;
//
//    private AdminBoardDTO newAdminBoard(Long i) {
//        AdminBoardDTO adminBoardDTO = new AdminBoardDTO();
//        adminBoardDTO.setHashTags("여름" + i);
//        adminBoardDTO.setSeason("여름");
//        adminBoardDTO.setWeather("더움");
//        adminBoardDTO.setTemp("27");
//        adminBoardDTO.setHeight(168);
//        adminBoardDTO.setGender("여");
//        adminBoardDTO.setBoardHits(0);
//        adminBoardDTO.setBoardLikes(0);
//        adminBoardDTO.setTop("상의" + i);
//        adminBoardDTO.setTopDetail("반팔" + i);
//        adminBoardDTO.setTopPrice(i*1000);
//        adminBoardDTO.setBottom("하의" + i);
//        adminBoardDTO.setBottomDetail("반바지" + i);
//        adminBoardDTO.setBottomPrice(i*1000);
//        adminBoardDTO.setEtc("악세사리" + i);
//        adminBoardDTO.setEtcDetail("스카프" + i);
//        adminBoardDTO.setEtcPrice(i*1000);
//        adminBoardDTO.setOriginalFileName("i");
//        adminBoardDTO.setStoredFileName("i");
//        return adminBoardDTO;
//    }
//
//    private AdminBoardDTO newAdminBoardInt(int i) {
//        AdminBoardDTO adminBoardDTO = new AdminBoardDTO();
//        adminBoardDTO.setHashTags("여름" + i);
//        adminBoardDTO.setSeason("여름");
//        adminBoardDTO.setWeather("더움");
//        adminBoardDTO.setTemp("27");
//        adminBoardDTO.setHeight(168);
//        adminBoardDTO.setGender("여");
//        adminBoardDTO.setBoardHits(0);
//        adminBoardDTO.setBoardLikes(0);
//        adminBoardDTO.setTop("상의" + i);
//        adminBoardDTO.setTopDetail("반팔" + i);
//        adminBoardDTO.setTopPrice(null);
//        adminBoardDTO.setBottom("하의" + i);
//        adminBoardDTO.setBottomDetail("반바지" + i);
//        adminBoardDTO.setBottomPrice(null);
//        adminBoardDTO.setEtc("악세사리" + i);
//        adminBoardDTO.setEtcDetail("스카프" + i);
//        adminBoardDTO.setEtcPrice(null);
//        adminBoardDTO.setOriginalFileName("i");
//        adminBoardDTO.setStoredFileName("i");
//        return adminBoardDTO;
//    }
//
//    private AdminBoardEntity adminBoardEntity (Long id) {
//        AdminBoardDTO adminBoardDTO = newAdminBoardInt(1);
//        AdminBoardEntity adminBoardEntity = AdminBoardEntity.toSaveEntity(adminBoardDTO);
//        return adminBoardEntity;
//    }
//
//adminBoardFileRepository.save(AdminBoardFileEntity.toSaveBoardFileEntity(null, "i", "i"));
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    @DisplayName("DB에 데이터 붓기-adminBoard")
//    public void adminBoardSave() {
//        LongStream.rangeClosed(1, 20).forEach(i -> {
//           adminBoardRepository.save(AdminBoardEntity.toSaveEntity(newAdminBoard(i)));
//        });
//
//    }


}
