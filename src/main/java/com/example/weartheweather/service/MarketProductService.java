package com.example.weartheweather.service;

import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.entity.AdminBoardFileEntity;
import com.example.weartheweather.entity.MarketProductEntity;
import com.example.weartheweather.entity.MarketProductFileEntity;
import com.example.weartheweather.repository.MarketProductFileRepository;
import com.example.weartheweather.repository.MarketProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketProductService {
    private final MarketProductRepository marketProductRepository;
    private final MarketProductFileRepository marketProductFileRepository;

    public Long save(MarketProductDTO marketProductDTO) throws IOException {
        if (marketProductDTO.getProductImage() == null || marketProductDTO.getProductImage().get(0).isEmpty()) {
            // 파일 없음
            MarketProductEntity marketProductEntity = MarketProductEntity.toSaveEntity(marketProductDTO);
            return marketProductRepository.save(marketProductEntity).getId();
        } else {
            // 파일 있음
            // 1. Board 테이블에 데이터를 먼저 저장
            MarketProductEntity marketProductEntity = MarketProductEntity.toSaveEntityWithFile(marketProductDTO);
            MarketProductEntity savedEntity = marketProductRepository.save(marketProductEntity);
            // 2. 파일이름 꺼내고, 저장용 이름 만들고 파일 로컬에 저장
            for (MultipartFile productImage : marketProductDTO.getProductImage()) {
                String originalFileName = productImage.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "C:\\data/weather_img\\" + storedFileName;
                productImage.transferTo(new File(savePath));
                // 3. BoardFileEntity로 변환 후 board_file_table에 저장
                // 자식 데이터를 저장할 때 반드시 부모의 id가 아닌 부모의 Entity 객체가 전달돼야 함.
                MarketProductFileEntity marketProductFileEntity =
                        MarketProductFileEntity.toSaveMarketProductFileEntity(savedEntity, originalFileName, storedFileName);
                marketProductFileRepository.save(marketProductFileEntity);
            }
            return savedEntity.getId();
        }
    }

    @Transactional
    public List<MarketProductDTO> findAll() {
        List<MarketProductEntity> marketProductEntityList = marketProductRepository.findAll();
        List<MarketProductDTO> marketProductDTOList = new ArrayList<>();
        marketProductEntityList.forEach(marketProductEntity -> {
            marketProductDTOList.add(MarketProductDTO.toDTO(marketProductEntity));
        });
        return marketProductDTOList;
    }

    @Transactional
    public MarketProductDTO findById(Long id) {
        MarketProductEntity marketProductEntity = marketProductRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return MarketProductDTO.toDTO(marketProductEntity);
    }

}