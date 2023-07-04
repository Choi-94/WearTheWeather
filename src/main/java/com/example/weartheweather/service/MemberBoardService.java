package com.example.weartheweather.service;

import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.entity.MemberBoardEntity;
import com.example.weartheweather.entity.MemberBoardFileEntity;
import com.example.weartheweather.entity.MemberEntity;
import com.example.weartheweather.repository.MemberBoardFileRepository;
import com.example.weartheweather.repository.MemberBoardRepository;
import com.example.weartheweather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberBoardService {
    private final MemberBoardRepository memberBoardRepository;
    private final MemberBoardFileRepository memberBoardFileRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberBoardDTO memberBoardDTO, String memberNickName) throws IOException {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        MemberBoardEntity memberBoardEntity = MemberBoardEntity.toSaveEntity(memberBoardDTO, memberEntity);
        MemberBoardEntity savedEntity = memberBoardRepository.save(memberBoardEntity);
        for (MultipartFile boardFile : memberBoardDTO.getBoardFile()) {
            String originalFileName = boardFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
            String savePath = "C:\\data/weather_img\\" + storedFileName;
            boardFile.transferTo(new File(savePath));
            MemberBoardFileEntity memberBoardFileEntity =
                    MemberBoardFileEntity.toSaveBoardFileEntity(savedEntity, originalFileName,storedFileName);
            memberBoardFileRepository.save(memberBoardFileEntity);
        }
    }
}
