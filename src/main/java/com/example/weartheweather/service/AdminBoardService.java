package com.example.weartheweather.service;

import com.example.weartheweather.dto.*;
import com.example.weartheweather.entity.*;
import com.example.weartheweather.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private final MemberBoardRepository memberBoardRepository;
    private final MarketProductRepository marketProductRepository;
    private final MarketLikesRepository marketLikesRepository;
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
    public void CookieBoardView(Long id, HttpServletRequest req, HttpServletResponse res) {
        /* 조회수 로직 */
        Cookie oldCookie = null;

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("adminBoardView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[[" + id.toString() + "]]")) {
                adminBoardRepository.updateHits(id);
                oldCookie.setValue(oldCookie.getValue() + "_[[" + id + "]]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                res.addCookie(oldCookie);
            }
        } else {
            adminBoardRepository.updateHits(id);
            Cookie newCookie = new Cookie("adminBoardView","[[" + id + "]]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            res.addCookie(newCookie);
        }
    }
    @Transactional
    public Page<AdminBoardDTO> findByBoardLikesNick(String memberNickName, Pageable pageable) {
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

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), adminBoardDTOList.size());
        Page<AdminBoardDTO> adminBoardDTOPage = new PageImpl<>(adminBoardDTOList.subList(start, end), pageable, adminBoardDTOList.size());

        return adminBoardDTOPage;
    }

    @Transactional
    public Page<MemberBoardDTO> findByMemberBoard(String memberNickName,Pageable pageable) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        List<MemberBoardEntity> memberBoardEntityList = memberBoardRepository.findByMemberEntity(memberEntity.get());
        List<MemberBoardDTO> memberBoardDTOList = new ArrayList<>();
        memberBoardEntityList.forEach(memberBoardEntity -> {
            memberBoardDTOList.add(MemberBoardDTO.toDTO(memberBoardEntity));
        });
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), memberBoardDTOList.size());
        Page<MemberBoardDTO> memberBoardDTOS = new PageImpl<>(memberBoardDTOList.subList(start, end), pageable, memberBoardDTOList.size());
        return memberBoardDTOS;
    }
    @Transactional
    public Page<MarketProductDTO> findByMarketProduct(String memberNickName,Pageable pageable) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        List<MarketLikesEntity> marketLikesEntityList = marketLikesRepository.findByMemberEntity(memberEntity.get());
        List<MarketLikesDTO> marketLikesDTOList = new ArrayList<>();
        marketLikesEntityList.forEach(marketLikesEntity -> {
        marketLikesDTOList.add(MarketLikesDTO.toDTO(marketLikesEntity));
        });
        List<MarketProductDTO> marketProductDTOList =new ArrayList<>();
        marketLikesDTOList.forEach(marketLikesDTO -> {
            Optional<MarketProductEntity> marketProductEntity = marketProductRepository.findById(marketLikesDTO.getBoardId());
            marketProductDTOList.add(MarketProductDTO.toDTO(marketProductEntity.get()));
        });
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), marketProductDTOList.size());
        Page<MarketProductDTO> marketProductDTOS = new PageImpl<>(marketProductDTOList.subList(start, end), pageable, marketProductDTOList.size());
            return marketProductDTOS;
    }
//        List<MarketProductEntity> marketProductEntityList = marketProductRepository.findByMemberEntity(memberEntity.get());
//        List<MarketProductDTO> marketProductDTOList = new ArrayList<>();
//        marketProductEntityList.forEach(marketProductEntity -> {
//            marketProductDTOList.add(MarketProductDTO.toDTO(marketProductEntity));
//        });
//        return marketProductDTOList;
//    }
}

