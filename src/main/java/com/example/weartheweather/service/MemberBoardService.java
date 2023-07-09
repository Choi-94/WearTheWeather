package com.example.weartheweather.service;


import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.dto.MemberBoardLikesDTO;
import com.example.weartheweather.entity.*;
import com.example.weartheweather.repository.MemberBoardFileRepository;
import com.example.weartheweather.repository.MemberBoardLikesRepository;
import com.example.weartheweather.repository.MemberBoardRepository;
import com.example.weartheweather.repository.MemberRepository;
import com.example.weartheweather.util.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional
    public void addBoardLikes(String memberNickName, Long id) {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        MemberBoardEntity memberBoardEntity = memberBoardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        memberBoardLikesRepository.save(MemberBoardLikesEntity.toSaveEntity(memberEntity, memberBoardEntity));
        memberBoardRepository.addBoardLikes(id);
    }

    @Transactional
    public void deleteBoardLikes(String memberNickName, Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        Optional<MemberBoardEntity> memberBoardEntity = memberBoardRepository.findById(id);
        memberBoardLikesRepository.deleteByMemberBoardEntityAndMemberEntity(memberBoardEntity, memberEntity);
        memberBoardRepository.deleteBoardLikes(id);
    }

    public void update(MemberBoardDTO memberBoardDTO, String memberNickName) {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        MemberBoardEntity memberBoardEntity = MemberBoardEntity.toUpdateEntity(memberBoardDTO, memberEntity);
        memberBoardRepository.save(memberBoardEntity);
    }

    public Page<MemberBoardDTO> paging(Pageable pageable, String type, String q) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 5;
        Page<MemberBoardEntity> boardEntities = null;
        if (type.equals("title")) {
            boardEntities = memberBoardRepository.findByBoardTitleContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else if (type.equals("writer")) {
            boardEntities = memberBoardRepository.findByBoardWriterContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            boardEntities = memberBoardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        }
        Page<MemberBoardDTO> boardDTOS = boardEntities.map(memberBoardEntity -> MemberBoardDTO.builder()
                .id(memberBoardEntity.getId())
                .boardTitle(memberBoardEntity.getBoardTitle())
                .boardWriter(memberBoardEntity.getBoardWriter())
                .createdAt(UtilClass.dateFormat(memberBoardEntity.getCreatedAt()))
                .boardHits(memberBoardEntity.getBoardHits())
                .build());
        return boardDTOS;
    }

    @Transactional
    public void viewCountUp(Long id) {
        memberBoardRepository.updateHits(id);
    }
}
