package com.example.weartheweather.service;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.dto.AdminBoardLikesDTO;
import com.example.weartheweather.entity.AdminBoardEntity;
import com.example.weartheweather.entity.AdminBoardFileEntity;
import com.example.weartheweather.entity.AdminBoardLikesEntity;
import com.example.weartheweather.entity.MemberEntity;
import com.example.weartheweather.repository.AdminBoardFileRepository;
import com.example.weartheweather.repository.AdminBoardLikesRepository;
import com.example.weartheweather.repository.AdminBoardRepository;
import com.example.weartheweather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminBoardService {
    private final AdminBoardRepository adminBoardRepository;
    private final AdminBoardFileRepository adminBoardFileRepository;
    private final MemberRepository memberRepository;
    private final AdminBoardLikesRepository adminBoardLikesRepository;
    public Long save(AdminBoardDTO adminBoardDTO) throws IOException {
        AdminBoardEntity adminBoardEntity = AdminBoardEntity.toSaveEntity(adminBoardDTO);
        AdminBoardEntity savedEntity = adminBoardRepository.save(adminBoardEntity);
        String originalFileName = adminBoardDTO.getBoardFile().getOriginalFilename();
        String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
        String savePath = "C:\\data/weather_img\\" + storedFileName;
        adminBoardDTO.getBoardFile().transferTo(new File(savePath));
        AdminBoardFileEntity adminBoardFileEntity =
                 AdminBoardFileEntity.toSaveBoardFileEntity(savedEntity, originalFileName, storedFileName);
        adminBoardFileRepository.save(adminBoardFileEntity);
        return savedEntity.getId();

    }

    @Transactional
    public List<AdminBoardDTO> findAll() {
        List<AdminBoardEntity> adminBoardEntityList = adminBoardRepository.findAll();
        System.out.println("서비스adminBoardEntityList = " + adminBoardEntityList);
        List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();
        adminBoardEntityList.forEach(adminBoardEntity -> {
            adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity));
        });
        return adminBoardDTOList;
    }
    @Transactional
    public AdminBoardDTO findById(Long id) {
        AdminBoardEntity adminBoardEntity = adminBoardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return AdminBoardDTO.toDTO(adminBoardEntity);
    }

    public void update(AdminBoardDTO adminBoardDTO) {
        AdminBoardEntity adminBoardEntity = AdminBoardEntity.toUpdateEntity(adminBoardDTO);
        adminBoardRepository.save(adminBoardEntity);
    }

    @Transactional
    public void updateHits(Long id) {
        adminBoardRepository.updateHits(id);
    }

    public void addBoardLikes(String memberNickName, Long id) {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        AdminBoardEntity adminBoardEntity = adminBoardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        adminBoardLikesRepository.save(AdminBoardLikesEntity.toSaveEntity(memberEntity, adminBoardEntity));
    }


    public int countBoardLikes(Long boardId) {
        AdminBoardEntity adminBoardEntity = adminBoardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException());
        return adminBoardLikesRepository.countBoardLikes(adminBoardEntity);
    }



    public AdminBoardLikesDTO findByBoardLikes(String memberNickName, Long boardId) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        Optional<AdminBoardEntity> adminBoardEntity = adminBoardRepository.findById(boardId);
        Optional<AdminBoardLikesEntity> optionalAdminBoardLikes = adminBoardLikesRepository.findByAdminBoardEntityAndMemberEntity(adminBoardEntity, memberEntity);
        if (optionalAdminBoardLikes.isPresent()) {
            AdminBoardLikesEntity adminBoardLikesEntity = optionalAdminBoardLikes.get();
            return AdminBoardLikesDTO.toDTO(adminBoardLikesEntity);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteBoardLikes(String memberNickName, Long boardId) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        Optional<AdminBoardEntity> adminBoardEntity = adminBoardRepository.findById(boardId);
        adminBoardLikesRepository.deleteByAdminBoardEntityAndMemberEntity(adminBoardEntity, memberEntity);
    }
    @Transactional
    public List<AdminBoardDTO> findByBoardLikesNick(String memberNickName) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        List<AdminBoardLikesEntity> adminBoardLikesEntityList = adminBoardLikesRepository.findByMemberEntity(memberEntity.get());
        List<AdminBoardLikesDTO> adminBoardLikesDTOList = new ArrayList<>();
        adminBoardLikesEntityList.forEach(adminBoardLikesEntity -> {
            adminBoardLikesDTOList.add(AdminBoardLikesDTO.toDTO(adminBoardLikesEntity));
        });
        List<AdminBoardDTO> adminBoardDTOList = new ArrayList<>();

        adminBoardLikesDTOList.forEach(adminBoardLikesDTO -> {
            Optional<AdminBoardEntity> adminBoardEntity = adminBoardRepository.findById(adminBoardLikesDTO.getBoardId());
            adminBoardDTOList.add(AdminBoardDTO.toDTO(adminBoardEntity.get()));
        });

        return adminBoardDTOList;
    }
}

