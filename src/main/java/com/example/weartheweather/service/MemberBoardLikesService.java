package com.example.weartheweather.service;

import com.example.weartheweather.entity.MemberEntity;
import com.example.weartheweather.repository.MemberBoardLikesRepository;
import com.example.weartheweather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberBoardLikesService {
    private final MemberRepository memberRepository;
    private final MemberBoardLikesRepository memberBoardLikesRepository;
    public Long totalLikes(String value) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(value).orElseThrow(() -> new NoSuchElementException());
        Long totalLikes = memberBoardLikesRepository.countByWriterEntity(memberEntity);
        return totalLikes;
    }

    public Long totalLikes2(String memberNickName) {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        Long totalLikes = memberBoardLikesRepository.countByWriterEntity(memberEntity);
        return totalLikes;
    }
}
