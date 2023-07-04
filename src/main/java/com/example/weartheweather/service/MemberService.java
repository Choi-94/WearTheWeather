package com.example.weartheweather.service;

import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.entity.MemberEntity;
import com.example.weartheweather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
//        MemberEntity memberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword())
//                .orElseThrow(() -> new NoSuchElementException("이메일 또는 비밀번호가 틀립니다"));
//        MemberDTO memberDTO1 = MemberDTO.tofindAll(memberEntity);
//        return memberDTO1;
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if(optionalMemberEntity.isPresent()){
            MemberDTO memberDTO1 = MemberDTO.tofindAll(optionalMemberEntity.get());
            return memberDTO1;
        }else{
            return null;
        }


    }


    public MemberDTO findByEmail(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);

        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.tofindAll(memberEntity);
            return memberDTO;
        } else {
            return null;
        }
    }


    public MemberDTO findById(Long value) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(value);
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.tofindAll(memberEntity);
            return memberDTO;
        }else{
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity);
    }
}

