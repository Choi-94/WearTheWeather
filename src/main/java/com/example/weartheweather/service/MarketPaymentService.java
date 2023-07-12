package com.example.weartheweather.service;
import com.example.weartheweather.dto.*;
import com.example.weartheweather.entity.*;
import com.example.weartheweather.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MarketPaymentService {
    private final MemberRepository memberRepository;
    private final MarketPaymentRepository marketPaymentRepository;
    private final MarketProductRepository marketProductRepository;
    private final MemberBoardService memberBoardService;

    public MarketProductEntity marketProductEntityFindById (Long id) {
        MarketProductEntity marketProductEntity = marketProductRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return marketProductEntity;
    }

    public MemberDTO updatePay(String memberNickName, Long memberWeatherPay) {
        MemberEntity loginMemberEntity = memberBoardService.findByMemberNickName(memberNickName);
        MemberDTO memberDTO = MemberDTO.toUpdatePay(loginMemberEntity, memberWeatherPay);
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        MemberEntity memberEntity1 = memberRepository.save(memberEntity);
        MemberDTO memberDTO1 = MemberDTO.tofindAll(memberEntity1);
        return memberDTO1;

    }
    @Transactional
    public void save(MarketPaymentDTO marketPaymentDTO, String memberNickName) {
        MemberEntity loginMemberEntity = memberBoardService.findByMemberNickName(memberNickName);
        MemberEntity writerMemberEntity = memberRepository.findById(marketPaymentDTO.getSellerId()).orElseThrow(() -> new NoSuchElementException());
        MarketProductEntity marketProductEntity = this.marketProductEntityFindById(marketPaymentDTO.getProductId());
        MarketPaymentEntity marketPaymentEntity = MarketPaymentEntity.toSaveEntity(marketPaymentDTO,loginMemberEntity,writerMemberEntity, marketProductEntity);
        Long balance = loginMemberEntity.getMemberWeatherPay() - marketProductEntity.getTotalAmount();
        memberRepository.updateMemberWeatherPay(loginMemberEntity.getId(), balance);
        marketPaymentRepository.save(marketPaymentEntity);
    }

    public MarketPaymentDTO findById(Long id) {
        MarketPaymentEntity marketPaymentEntity = marketPaymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return MarketPaymentDTO.toDTO(marketPaymentEntity);
    }


    public MarketPaymentDTO findByProductId(Long productId, String memberNickName) {
        MemberEntity memberEntity = memberBoardService.findByMemberNickName(memberNickName);
        MarketProductEntity marketProductEntity = this.marketProductEntityFindById(productId);
        MarketPaymentEntity marketPaymentEntity = marketPaymentRepository.findByMemberEntityAndMarketProductEntity(memberEntity, marketProductEntity);
        return MarketPaymentDTO.toDTO(marketPaymentEntity);
    }
}

