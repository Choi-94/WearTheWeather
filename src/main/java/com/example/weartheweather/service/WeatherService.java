package com.example.weartheweather.service;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.repository.AdminBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final AdminBoardRepository adminBoardRepository;

    public List<AdminBoardDTO> findWeather(String day) {
       List<AdminBoardEntity> adminBoardEntityList =  adminBoardRepository.findAll();

       List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();
       adminBoardEntityList.forEach(adminBoardEntity -> {
           adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity));
       });
       List<AdminBoardDTO> adminBoardDTOList1 = new ArrayList<>();
       adminBoardDTOList.forEach(adminBoardDTO -> {
          if(adminBoardDTO.getSeason().contains(day)){
              adminBoardDTOList1.add(adminBoardDTO);
          }
       });
       return adminBoardDTOList1;
    }

}
