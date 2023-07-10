package com.example.weartheweather.service;

import com.example.weartheweather.dto.MarketLikesDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberBoardLikesDTO;
import com.example.weartheweather.entity.*;
import com.example.weartheweather.repository.MarketLikesRepository;
import com.example.weartheweather.repository.MarketProductFileRepository;
import com.example.weartheweather.repository.MarketProductRepository;
import com.example.weartheweather.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final MarketLikesRepository marketLikesRepository;

    public Long save(MarketProductDTO marketProductDTO, String memberNickName) throws IOException {
        if (marketProductDTO.getProductImage() == null || marketProductDTO.getProductImage().get(0).isEmpty()) {
            // 파일없음
            MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
            MarketProductEntity marketProductEntity = MarketProductEntity.toSaveEntity(marketProductDTO, memberEntity);
            return marketProductRepository.save(marketProductEntity).getId();
        } else {
            // 파일 있음
            // 1. Board 테이블에 데이터를 먼저 저장
            MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
            MarketProductEntity marketProductEntity = MarketProductEntity.toSaveEntityWithFile(marketProductDTO, memberEntity);
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

    @Transactional
    public List<MarketProductDTO> findByProductWriter(String productWriter) {
        List<MarketProductEntity> marketProductEntityList = marketProductRepository.findByProductWriter(productWriter);
        List<MarketProductDTO> marketProductDTOList = new ArrayList<>();
        marketProductEntityList.forEach(marketProductEntity -> {
            marketProductDTOList.add(MarketProductDTO.toDTO(marketProductEntity));
        });
        return marketProductDTOList;
    }

    public MarketLikesDTO findByMarketLikes(String memberNickName, Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        Optional<MarketProductEntity> marketProductEntity = marketProductRepository.findById(id);
        Optional<MarketLikesEntity> optionalMarketLikes = marketLikesRepository.findByMarketProductEntityAndMemberEntity(marketProductEntity, memberEntity);
        if (optionalMarketLikes.isPresent()) {
            MarketLikesEntity marketLikesEntity = optionalMarketLikes.get();
            return MarketLikesDTO.toDTO(marketLikesEntity);
        } else {
            return null;
        }
    }

    @Transactional
    public int countMarketLikes(Long id) {
        MarketProductEntity marketProductEntity = marketProductRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return marketLikesRepository.countMarketLikes(marketProductEntity);
    }
    @Transactional
    public void addMarketLikes(String memberNickName, Long id) {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        MarketProductEntity marketProductEntity =  marketProductRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        marketLikesRepository.save(MarketLikesEntity.toSaveEntity(memberEntity, marketProductEntity));
        marketProductRepository.addMarketLikes(id);
    }

    @Transactional
    public void deleteMarketLikes(String memberNickName, Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        Optional<MarketProductEntity> marketProductEntity = marketProductRepository.findById(id);
        marketLikesRepository.deleteByMarketProductEntityAndMemberEntity(marketProductEntity, memberEntity);
        marketProductRepository.deleteMarketLikes(id);
    }

    @Transactional
    public void updateHits(Long id) {
        marketProductRepository.updateHits(id);
    }
}