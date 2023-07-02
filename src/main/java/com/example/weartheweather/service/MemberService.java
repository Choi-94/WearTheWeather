package com.example.weartheweather.service;

import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.entity.MemberEntity;
import com.example.weartheweather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public int emailcheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isPresent()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int nickcheck(String memberNickName) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberNickName(memberNickName);
        if (optionalMemberEntity.isPresent()) {
            return 1;
        } else {
            return 0;
        }
    }

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        memberRepository.save(memberEntity);
    }


    public MemberDTO loginAxios(MemberDTO memberDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword())
                .orElseThrow(() -> new NoSuchElementException("이메일 또는 비밀번호가 틀립니다"));
        MemberDTO memberDTO1 = MemberDTO.tofindAll(memberEntity);
        return memberDTO1;
    }


    public MemberDTO findByEmail(String memberEmail) {
        MemberDTO memberDTO = MemberDTO.tofindAll(memberRepository.findByMemberEmail(memberEmail).orElseThrow(()-> new NoSuchElementException()));


        return memberDTO;
    }
}
