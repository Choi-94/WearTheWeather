package com.example.weartheweather.component;

import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.entity.MemberEntity;
import com.example.weartheweather.repository.MemberRepository;
import com.example.weartheweather.service.MemberBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class PointScheduler {
    private final MemberRepository memberRepository;
    private final MemberBoardService memberBoardService;

    @Scheduled(cron = "0 0 9 * * 2")
    public void givePointsToMembers() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime lastWeek = today.minusWeeks(1);
        List<MemberBoardDTO> weeklyLikesList = memberBoardService.weeklyLikesList(today, lastWeek);
        List<MemberBoardDTO> rankingTop5 = weeklyLikesList.subList(0, Math.min(5, weeklyLikesList.size()));
        System.out.println("rankingTop5 = " + rankingTop5);
        List<MemberEntity> memberEntityList = new ArrayList<>();

        for (MemberBoardDTO memberBoardDTO : rankingTop5) {
            MemberEntity memberEntity = memberRepository.findById(memberBoardDTO.getWriterId()).orElseThrow(() -> new NoSuchElementException());
            memberEntityList.add(memberEntity);
        }

        for (MemberEntity member : memberEntityList) {
            member.setMemberPoints(member.getMemberPoints() + 1000);
            memberRepository.save(member);
        }
    }
}
