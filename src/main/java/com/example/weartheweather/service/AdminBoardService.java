package com.example.weartheweather.service;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.repository.AdminBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBoardService {
    private final AdminBoardRepository adminBoardRepository;
    public Long save(AdminBoardDTO adminBoardDTO) {
        AdminBoardEntity adminBoardEntity = AdminBoardEntity.toSaveEntity(adminBoardDTO);
        adminBoardRepository.save(adminBoardEntity);
        return adminBoardDTO.getId();

    }
}
