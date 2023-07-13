package com.example.weartheweather.service;

import com.example.weartheweather.dto.AlarmDTO;
import com.example.weartheweather.entity.AlarmEntity;
import com.example.weartheweather.entity.MemberBoardEntity;
import com.example.weartheweather.entity.MemberEntity;
import com.example.weartheweather.repository.AlarmRepository;
import com.example.weartheweather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final MemberBoardService memberBoardService;
    private final MemberRepository memberRepository;

    public void likesAlarm(AlarmDTO alarmDTO, String memberNickName) {
        MemberEntity writerMemberEntity = memberRepository.findById(alarmDTO.getWriterId()).orElseThrow(() -> new NoSuchElementException());
        MemberEntity loginMemberEntity = memberBoardService.findByMemberNickName(memberNickName);
        MemberBoardEntity memberBoardEntity = memberBoardService.findByMemberBoardId(alarmDTO.getBoardId());
        String type = "Likes";
        AlarmEntity alarmEntity = AlarmEntity.toSaveEntity(writerMemberEntity, loginMemberEntity, memberBoardEntity, type);
        alarmRepository.save(alarmEntity);
    }

    public List<AlarmDTO> findByMyAlarm(String memberNickName) {
        MemberEntity loginMemberEntity = memberBoardService.findByMemberNickName(memberNickName);
        List<AlarmEntity> alarmEntityList = alarmRepository.findByWriterMemberEntity(loginMemberEntity);
        List<AlarmDTO> alarmDTOList = new ArrayList<>();
        alarmEntityList.forEach(alarmEntity -> {
            alarmDTOList.add(AlarmDTO.toDTO(alarmEntity));
        });
        return alarmDTOList;
    }
}