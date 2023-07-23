package com.example.weartheweather.service;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.repository.AdminBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final AdminBoardRepository adminBoardRepository;

    @Transactional
    public List<AdminBoardDTO> findWeatherD0(String day) {
        System.out.println("day = " + day.toLowerCase());
       List<AdminBoardEntity> adminBoardEntityList =  adminBoardRepository.findAll();

       List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();
       adminBoardEntityList.forEach(adminBoardEntity -> {
           adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity));
       });
       List<AdminBoardDTO> adminBoardDTOList1 = new ArrayList<>();
       adminBoardDTOList.forEach(adminBoardDTO -> {
          if(adminBoardDTO.getWeather().contains(day)) {
              adminBoardDTOList1.add(adminBoardDTO);

          }
       });

       return adminBoardDTOList1;
    }
    @Transactional
    public List<AdminBoardDTO> findWeatherD1(String day) {
        System.out.println("day = " + day.toLowerCase());
        List<AdminBoardEntity> adminBoardEntityList =  adminBoardRepository.findAll();

        List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();
        adminBoardEntityList.forEach(adminBoardEntity -> {
            adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity));
        });
        List<AdminBoardDTO> adminBoardDTOList1 = new ArrayList<>();
        adminBoardDTOList.forEach(adminBoardDTO -> {
            if(adminBoardDTO.getWeather().contains(day)) {
                adminBoardDTOList1.add(adminBoardDTO);

            }
        });

        return adminBoardDTOList1;
    }
    @Transactional
    public List<AdminBoardDTO> findWeatherD2(String day) {
        System.out.println("day = " + day.toLowerCase());
        List<AdminBoardEntity> adminBoardEntityList =  adminBoardRepository.findAll();

        List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();
        adminBoardEntityList.forEach(adminBoardEntity -> {
            adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity));
        });
        List<AdminBoardDTO> adminBoardDTOList1 = new ArrayList<>();
        adminBoardDTOList.forEach(adminBoardDTO -> {
            if(adminBoardDTO.getWeather().contains(day)) {
                adminBoardDTOList1.add(adminBoardDTO);

            }
        });

        return adminBoardDTOList1;
    } @Transactional
    public List<AdminBoardDTO> findWeatherD3(String day) {
        System.out.println("day = " + day.toLowerCase());
        List<AdminBoardEntity> adminBoardEntityList =  adminBoardRepository.findAll();

        List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();
        adminBoardEntityList.forEach(adminBoardEntity -> {
            adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity));
        });
        List<AdminBoardDTO> adminBoardDTOList1 = new ArrayList<>();
        adminBoardDTOList.forEach(adminBoardDTO -> {
            if(adminBoardDTO.getWeather().contains(day)) {
                adminBoardDTOList1.add(adminBoardDTO);

            }
        });

        return adminBoardDTOList1;
    } @Transactional
    public List<AdminBoardDTO> findWeatherD4(String day) {
        System.out.println("day = " + day.toLowerCase());
        List<AdminBoardEntity> adminBoardEntityList =  adminBoardRepository.findAll();

        List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();
        adminBoardEntityList.forEach(adminBoardEntity -> {
            adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity));
        });
        List<AdminBoardDTO> adminBoardDTOList1 = new ArrayList<>();
        adminBoardDTOList.forEach(adminBoardDTO -> {
            if(adminBoardDTO.getWeather().contains(day)) {
                adminBoardDTOList1.add(adminBoardDTO);

            }
        });

        return adminBoardDTOList1;
    }
    @Transactional
    public List<AdminBoardDTO> findWeatherD5(String day) {
        System.out.println("day = " + day.toLowerCase());
        List<AdminBoardEntity> adminBoardEntityList =  adminBoardRepository.findAll();

        List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();
        adminBoardEntityList.forEach(adminBoardEntity -> {
            adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity));
        });
        List<AdminBoardDTO> adminBoardDTOList1 = new ArrayList<>();
        adminBoardDTOList.forEach(adminBoardDTO -> {
            if(adminBoardDTO.getWeather().contains(day)) {
                adminBoardDTOList1.add(adminBoardDTO);

            }
        });

        return adminBoardDTOList1;
    }
    @Transactional
    public List<AdminBoardDTO> findWeatherD6(String day) {
        System.out.println("day = " + day.toLowerCase());
        List<AdminBoardEntity> adminBoardEntityList =  adminBoardRepository.findAll();

        List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();
        adminBoardEntityList.forEach(adminBoardEntity -> {
            adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity));
        });
        List<AdminBoardDTO> adminBoardDTOList1 = new ArrayList<>();
        adminBoardDTOList.forEach(adminBoardDTO -> {
            if(adminBoardDTO.getWeather().contains(day)) {
                adminBoardDTOList1.add(adminBoardDTO);

            }
        });

        return adminBoardDTOList1;
    }





}
