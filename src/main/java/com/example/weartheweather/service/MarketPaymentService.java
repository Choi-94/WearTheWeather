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
        MemberEntity sellerMemberEntity = memberBoardService.findByMemberNickName(marketPaymentDTO.getSellerWriter());
//        MemberEntity writerMemberEntity = memberRepository.findById(sellerMemberEntity.getId()).orElseThrow(() -> new NoSuchElementException());
        MarketProductEntity marketProductEntity = this.marketProductEntityFindById(marketPaymentDTO.getProductId());
        MarketPaymentEntity marketPaymentEntity = MarketPaymentEntity.toSaveEntity(marketPaymentDTO,loginMemberEntity,sellerMemberEntity, marketProductEntity);
        marketPaymentRepository.save(marketPaymentEntity);

        Long balance = loginMemberEntity.getMemberWeatherPay() - marketProductEntity.getTotalAmount();
        memberRepository.updateMemberWeatherPay(loginMemberEntity.getId(), balance);

    }

    public MarketPaymentDTO findById(Long id) {
        MarketPaymentEntity marketPaymentEntity = marketPaymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return MarketPaymentDTO.toDTO(marketPaymentEntity);
    }


    @Transactional
    public MarketPaymentDTO findByProductId(Long productId, String memberNickName) {
        MemberEntity memberEntity = memberBoardService.findByMemberNickName(memberNickName);
        MarketProductEntity marketProductEntity = this.marketProductEntityFindById(productId);
        MarketPaymentEntity marketPaymentEntity = marketPaymentRepository.findByMemberEntityAndMarketProductEntity(memberEntity, marketProductEntity);
        return MarketPaymentDTO.toDTO(marketPaymentEntity);
    }
@Transactional
    public void buyConfirm(Long paymentId, String adminId) {
        Optional<MarketPaymentEntity> marketPaymentEntity = marketPaymentRepository.findById(paymentId);
        System.out.println("marketPaymentEntity = " + marketPaymentEntity.get());
        // 밑에값을 DTO로 변환해서 저장해야하는지 의문 --> 나중에 error가 뜨면 확인해야함
        MarketPaymentDTO marketPaymentDTO = MarketPaymentDTO.toDTO(marketPaymentEntity.get());
        System.out.println("marketPaymentDTO = " + marketPaymentDTO);
        Optional<MarketProductEntity> marketProductEntity = marketProductRepository.findById(marketPaymentDTO.getProductId());
        Optional<MemberEntity> memberEntity = memberRepository.findById(marketPaymentDTO.getSellerId());


        Long ProductPrice = marketProductEntity.get().getProductPrice();
        System.out.println("ProductPrice = " + ProductPrice);
        Long TotalAmount = marketProductEntity.get().getTotalAmount();
        System.out.println("TotalAmount = " + TotalAmount);
        Long sellerId = memberEntity.get().getId();
        //관리자 weatherpay 처리 코드
        System.out.println("어드민"+adminId);
        Optional<MemberEntity> memberAdminEntity = memberRepository.findByMemberEmail(adminId);
        MemberDTO memberDTO = MemberDTO.updatePayConfirm(memberAdminEntity.get(),ProductPrice);
        MemberEntity updateEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(updateEntity);

        //판매자 weatherpay 처리 코드
        Optional<MemberEntity> memberEntitySeller = memberRepository.findById(sellerId);
        MemberDTO membersellerDTO = MemberDTO.updateSellerPay(memberEntitySeller.get(),ProductPrice);
        MemberEntity updateSellerEntity = MemberEntity.toUpdateEntity(membersellerDTO);
        memberRepository.save(updateSellerEntity);

        //paymententity tradeStatus값 변환 처리 코드
       MarketPaymentEntity marketStatusPayment = MarketPaymentEntity.toStatusUpdateEntity(marketPaymentEntity.get());
       marketPaymentRepository.save(marketStatusPayment);

    }

    public void paymentAdmin(String adminId, Long totalAmount) {
        Optional<MemberEntity> memberAdminEntity = memberRepository.findByMemberEmail(adminId);
        MemberDTO memberDTO = MemberDTO.updatePay(memberAdminEntity.get(),totalAmount);
        MemberEntity updateEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(updateEntity);

    }
}

