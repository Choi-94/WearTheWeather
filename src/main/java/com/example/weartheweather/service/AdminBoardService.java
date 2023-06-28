package com.example.weartheweather.service;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.entity.AdminBoardFileEntity;
import com.example.weartheweather.repository.AdminBoardFileRepository;
import com.example.weartheweather.repository.AdminBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBoardService {
    private final AdminBoardRepository adminBoardRepository;
    private final AdminBoardFileRepository adminBoardFileRepository;
    public Long save(AdminBoardDTO adminBoardDTO) throws IOException {
        AdminBoardEntity adminBoardEntity = AdminBoardEntity.toSaveEntity(adminBoardDTO);
        AdminBoardEntity savedEntity = adminBoardRepository.save(adminBoardEntity);
        String originalFileName = adminBoardDTO.getBoardFile().getOriginalFilename();
        String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
        String savePath = "C:\\data/weather_img\\" + storedFileName;
        adminBoardDTO.getBoardFile().transferTo(new File(savePath));
        AdminBoardFileEntity adminBoardFileEntity =
                 AdminBoardFileEntity.toSaveBoardFileEntity(savedEntity, originalFileName, storedFileName);
        adminBoardFileRepository.save(adminBoardFileEntity);
        return savedEntity.getId();

    }

    public List<AdminBoardDTO> findAll() {
        List<AdminBoardEntity> adminBoardEntityList = adminBoardRepository.findAll();
        List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();
        adminBoardEntityList.forEach(adminBoardEntity -> {
            adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity));
        });
        return adminBoardDTOList;
    }
}
