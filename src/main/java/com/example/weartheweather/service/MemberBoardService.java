package com.example.weartheweather.service;

import com.example.weartheweather.dto.BoardRankingDTO;
import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.dto.MemberBoardLikesDTO;
import com.example.weartheweather.dto.PopularKeywordsDTO;
import com.example.weartheweather.entity.*;
import com.example.weartheweather.repository.MemberBoardFileRepository;
import com.example.weartheweather.repository.MemberBoardLikesRepository;
import com.example.weartheweather.repository.MemberBoardRepository;
import com.example.weartheweather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MemberBoardService {
    private final MemberBoardRepository memberBoardRepository;
    private final MemberBoardFileRepository memberBoardFileRepository;
    private final MemberBoardLikesRepository memberBoardLikesRepository;
    private final MemberRepository memberRepository;


    public MemberEntity findByMemberNickName(String memberNickName) {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        return memberEntity;
    }

    public MemberBoardEntity findByMemberBoardId(Long id) {
        MemberBoardEntity memberBoardEntity = memberBoardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return memberBoardEntity;
    }

    @Transactional
    public void save(MemberBoardDTO memberBoardDTO, String memberNickName) throws IOException {
        MemberEntity memberEntity = this.findByMemberNickName(memberNickName);
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
    public Page<MemberBoardDTO> findAll(Pageable pageable, String type, String q) {
        List<MemberBoardEntity> memberBoardEntityList = memberBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<MemberBoardDTO> memberBoardDTOList = new ArrayList<>();
        memberBoardEntityList.forEach(memberBoardEntity -> {
            memberBoardDTOList.add(MemberBoardDTO.toDTO(memberBoardEntity));
        });

        // Filter adminBoardDTOList based on search criteria
        List<MemberBoardDTO> filtermemberBoardDTOList = new ArrayList<>();
        if(type.equals("title")){
            filtermemberBoardDTOList = memberBoardDTOList.stream().filter(memberBoardDTO -> memberBoardDTO.getBoardTitle().contains(q))
                    .collect(Collectors.toList());
        }else if(type.equals("writer")){
            filtermemberBoardDTOList = memberBoardDTOList.stream().filter(memberBoardDTO -> memberBoardDTO.getBoardWriter().contains(q))
                    .collect(Collectors.toList());
        }else{
            filtermemberBoardDTOList = memberBoardDTOList;
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtermemberBoardDTOList.size());
        Page<MemberBoardDTO> memberBoardDTOPage  = new PageImpl<>(filtermemberBoardDTOList.subList(start, end), pageable, filtermemberBoardDTOList.size());
        return memberBoardDTOPage ;
    }

    public MemberBoardLikesDTO findByBoardLikes(String memberNickName, Long id) {
        MemberEntity memberEntity = this.findByMemberNickName(memberNickName);
        MemberBoardEntity memberBoardEntity = this.findByMemberBoardId(id);
        MemberBoardLikesEntity memberBoardLikesEntity = memberBoardLikesRepository.findByMemberBoardEntityAndMemberEntity(memberBoardEntity, memberEntity);
        if (memberBoardLikesEntity != null) {
            return MemberBoardLikesDTO.toDTO(memberBoardLikesEntity);
        } else {
            return null;
        }
    }

    @Transactional
    public int countBoardLikes(Long id) {
        MemberBoardEntity memberBoardEntity = this.findByMemberBoardId(id);
        return memberBoardLikesRepository.countBoardLikes(memberBoardEntity);
    }

    @Transactional
    public MemberBoardDTO findById(Long id) {
        MemberBoardEntity memberBoardEntity = this.findByMemberBoardId(id);
        return MemberBoardDTO.toDTO(memberBoardEntity);
    }

    @Transactional
    public void addBoardLikes(String memberNickName, Long id) {
        MemberEntity memberEntity = this.findByMemberNickName(memberNickName);
        MemberBoardEntity memberBoardEntity = this.findByMemberBoardId(id);
        memberBoardLikesRepository.save(MemberBoardLikesEntity.toSaveEntity(memberEntity, memberBoardEntity));
        memberBoardRepository.addBoardLikes(id);
    }


    @Transactional
    public void deleteBoardLikes(String memberNickName, Long id) {
        MemberEntity memberEntity = this.findByMemberNickName(memberNickName);
        MemberBoardEntity memberBoardEntity = this.findByMemberBoardId(id);
        memberBoardLikesRepository.deleteByMemberBoardEntityAndMemberEntity(memberBoardEntity, memberEntity);
        memberBoardRepository.deleteBoardLikes(id);
    }

    public void update(MemberBoardDTO memberBoardDTO, String memberNickName) {
        MemberEntity memberEntity = this.findByMemberNickName(memberNickName);
        MemberBoardEntity memberBoardEntity = MemberBoardEntity.toUpdateEntity(memberBoardDTO, memberEntity);
        memberBoardRepository.save(memberBoardEntity);
    }

    @Transactional
    public void CookieBoardView(Long id, HttpServletRequest req, HttpServletResponse res) {
        /* 조회수 로직 */
        Cookie oldCookie = null;

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("boardView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                memberBoardRepository.updateHits(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                res.addCookie(oldCookie);
            }
        } else {
            memberBoardRepository.updateHits(id);
            Cookie newCookie = new Cookie("boardView","[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            res.addCookie(newCookie);
        }
    }

    @Transactional
    public List<MemberBoardDTO> weeklyLikesList(LocalDateTime today, LocalDateTime lastWeek) {
        List<MemberBoardLikesEntity> weeklyLikesEntityList = memberBoardLikesRepository.findByCreatedAtBetween(lastWeek, today);

        Map<MemberBoardEntity, List<MemberBoardLikesEntity>> groupedLikesByBoardEntity =
                weeklyLikesEntityList.stream()
                        .collect(Collectors.groupingBy(MemberBoardLikesEntity::getMemberBoardEntity));

        List<BoardRankingDTO> boardRankingDTOList = new ArrayList<>();

        for (Map.Entry<MemberBoardEntity, List<MemberBoardLikesEntity>> entry : groupedLikesByBoardEntity.entrySet()) {
            MemberBoardEntity memberBoardEntity = entry.getKey();
            List<MemberBoardLikesEntity> likesList = entry.getValue();


            BoardRankingDTO boardRankingDTO = new BoardRankingDTO();
            boardRankingDTO.setBoardId(memberBoardEntity.getId());
            boardRankingDTO.setLikeCount(likesList.size());
            boardRankingDTOList.add(boardRankingDTO);
        }
        boardRankingDTOList = boardRankingDTOList.stream()
                .limit(20)
                .collect(Collectors.toList());

        boardRankingDTOList.sort(Comparator.comparingInt(BoardRankingDTO::getLikeCount).reversed());
        System.out.println("boardRankingDTOList = " + boardRankingDTOList);
        List<MemberBoardDTO> memberBoardDTOList = new ArrayList<>();
        boardRankingDTOList.forEach(boardRankingDTO -> {
            Optional<MemberBoardEntity> memberBoardEntity = memberBoardRepository.findById(boardRankingDTO.getBoardId());

            memberBoardDTOList.add(MemberBoardDTO.toDTO(memberBoardEntity.get()));

        });

        return memberBoardDTOList;
    }


    public void delete(Long id) {
        memberBoardRepository.deleteById(id);
    }
}
