package com.example.weartheweather.service;
import com.example.weartheweather.dto.*;
import com.example.weartheweather.entity.*;
import com.example.weartheweather.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MarketPaymentService {
    private final MemberRepository memberRepository;
    private final MarketPaymentRepository marketPaymentRepository;

    public MemberDTO updatePay(String memberNickName, Long memberWeatherPay) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberNickName(memberNickName);
        MemberDTO memberDTO = MemberDTO.toUpdatePay(optionalMemberEntity.get(), memberWeatherPay);
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        MemberEntity memberEntity1 = memberRepository.save(memberEntity);
        MemberDTO memberDTO1 = MemberDTO.tofindAll(memberEntity1);
        return memberDTO1;

    }
    @Transactional
    public void save(MarketPaymentDTO marketPaymentDTO, String memberNickName) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberNickName(memberNickName);
        Optional<MemberEntity> optionalMemberEntity1 = memberRepository.findByMemberNickName(marketPaymentDTO.getProductWriter());
        MarketPaymentEntity marketPaymentEntity = MarketPaymentEntity.toSaveEntity(marketPaymentDTO,optionalMemberEntity.get(),optionalMemberEntity1.get());
        marketPaymentRepository.save(marketPaymentEntity);
    }


//    public void save(MarketPaymentDTO marketPaymentDTO) {
//        MarketPaymentEntity marketPaymentEntity = MarketPaymentEntity.toSaveEntity(marketPaymentDTO);
//        marketPaymentRepository.save(marketPaymentEntity);
//    }
}

