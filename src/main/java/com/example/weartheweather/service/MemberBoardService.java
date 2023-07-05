package com.example.weartheweather.service;

import com.example.weartheweather.dto.AdminBoardLikesDTO;
import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.dto.MemberBoardLikesDTO;
import com.example.weartheweather.entity.*;
import com.example.weartheweather.repository.MemberBoardFileRepository;
import com.example.weartheweather.repository.MemberBoardLikesRepository;
import com.example.weartheweather.repository.MemberBoardRepository;
import com.example.weartheweather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberBoardService {
    private final MemberBoardRepository memberBoardRepository;
    private final MemberBoardFileRepository memberBoardFileRepository;
    private final MemberBoardLikesRepository memberBoardLikesRepository;
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

    @Transactional
    public List<MemberBoardDTO> findAll() {
        List<MemberBoardEntity> memberBoardEntityList = memberBoardRepository.findAll();
        List<MemberBoardDTO> memberBoardDTOList = new ArrayList<>();
        memberBoardEntityList.forEach(memberBoardEntity -> {
            memberBoardDTOList.add(MemberBoardDTO.toDTO(memberBoardEntity));
        });
        return memberBoardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        memberBoardRepository.updateHits(id);
    }

    public MemberBoardLikesDTO findByBoardLikes(String memberNickName, Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        Optional<MemberBoardEntity> memberBoardEntity = memberBoardRepository.findById(id);
        Optional<MemberBoardLikesEntity> optionalMemberBoardLikes = memberBoardLikesRepository.findByMemberBoardEntityAndMemberEntity(memberBoardEntity, memberEntity);
        if (optionalMemberBoardLikes.isPresent()) {
            MemberBoardLikesEntity memberBoardLikesEntity = optionalMemberBoardLikes.get();
            return MemberBoardLikesDTO.toDTO(memberBoardLikesEntity);
        } else {
            return null;
        }
    }

    @Transactional
    public int countBoardLikes(Long id) {
        MemberBoardEntity memberBoardEntity = memberBoardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return memberBoardLikesRepository.countBoardLikes(memberBoardEntity);
    }

    @Transactional
    public MemberBoardDTO findById(Long id) {
        MemberBoardEntity memberBoardEntity = memberBoardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return MemberBoardDTO.toDTO(memberBoardEntity);
    }

    public void addBoardLikes(String memberNickName, Long id) {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        MemberBoardEntity memberBoardEntity = memberBoardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        memberBoardLikesRepository.save(MemberBoardLikesEntity.toSaveEntity(memberEntity, memberBoardEntity));
    }

    @Transactional
    public void deleteBoardLikes(String memberNickName, Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        Optional<MemberBoardEntity> memberBoardEntity = memberBoardRepository.findById(id);
        memberBoardLikesRepository.deleteByMemberBoardEntityAndMemberEntity(memberBoardEntity, memberEntity);
    }
}
