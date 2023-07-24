package com.example.weartheweather.service;

import com.example.weartheweather.dto.MarketLikesDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.entity.*;
import com.example.weartheweather.repository.MarketLikesRepository;
import com.example.weartheweather.repository.MarketProductFileRepository;
import com.example.weartheweather.repository.MarketProductRepository;
import com.example.weartheweather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketProductService {
    private final MarketProductRepository marketProductRepository;
    private final MarketProductFileRepository marketProductFileRepository;
    private final MemberRepository memberRepository;
    private final MarketLikesRepository marketLikesRepository;

    public Long save(MarketProductDTO marketProductDTO, String memberNickName) throws IOException {
        if (marketProductDTO.getProductImage() == null || marketProductDTO.getProductImage().get(0).isEmpty()) {
            // 파일없음
            MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
            MarketProductEntity marketProductEntity = MarketProductEntity.toSaveEntity(marketProductDTO, memberEntity);
            return marketProductRepository.save(marketProductEntity).getId();
        } else {
            // 파일 있음
            // 1. Board 테이블에 데이터를 먼저 저장
            MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
            MarketProductEntity marketProductEntity = MarketProductEntity.toSaveEntityWithFile(marketProductDTO, memberEntity);
            MarketProductEntity savedEntity = marketProductRepository.save(marketProductEntity);
            // 2. 파일이름 꺼내고, 저장용 이름 만들고 파일 로컬에 저장
            for (MultipartFile productImage : marketProductDTO.getProductImage()) {
                String originalFileName = productImage.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "C:\\data/weather_img\\" + storedFileName;
                productImage.transferTo(new File(savePath));
                // 3. BoardFileEntity로 변환 후 board_file_table에 저장
                // 자식 데이터를 저장할 때 반드시 부모의 id가 아닌 부모의 Entity 객체가 전달돼야 함.
                MarketProductFileEntity marketProductFileEntity =
                        MarketProductFileEntity.toSaveMarketProductFileEntity(savedEntity, originalFileName, storedFileName);
                marketProductFileRepository.save(marketProductFileEntity);
            }
            return savedEntity.getId();
        }
    }

    @Transactional
    public Page<MarketProductDTO> findAll(Pageable pageable, String type, String q) {
        List<MarketProductEntity> marketProductEntityList = marketProductRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<MarketProductDTO> marketProductDTOList = new ArrayList<>();
        marketProductEntityList.forEach(marketProductEntity -> {
            marketProductDTOList.add(MarketProductDTO.toDTO(marketProductEntity));
        });
        List<MarketProductDTO> filterMarketProductList = new ArrayList<>();
        if(type.equals("title")){
            filterMarketProductList = marketProductDTOList.stream().filter(marketProductDTO -> marketProductDTO.getProductTitle().contains(q))
                    .collect(Collectors.toList());
        }else if(type.equals("writer")){
            filterMarketProductList = marketProductDTOList.stream().filter(marketProductDTO -> marketProductDTO.getProductWriter().contains(q))
                    .collect(Collectors.toList());
        }else{
            filterMarketProductList = marketProductDTOList;
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filterMarketProductList.size());
        Page<MarketProductDTO> marketProductDTOS  = new PageImpl<>(filterMarketProductList.subList(start, end), pageable, filterMarketProductList.size());
        return marketProductDTOS ;

    }
//    @javax.transaction.Transactional
//    public Page<MemberBoardDTO> findAll(Pageable pageable, String type, String q) {
//        List<MemberBoardEntity> memberBoardEntityList = memberBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
//        List<MemberBoardDTO> memberBoardDTOList = new ArrayList<>();
//        memberBoardEntityList.forEach(memberBoardEntity -> {
//            memberBoardDTOList.add(MemberBoardDTO.toDTO(memberBoardEntity));
//        });
//
//        // Filter adminBoardDTOList based on search criteria
//        List<MemberBoardDTO> filtermemberBoardDTOList = new ArrayList<>();
//        if(type.equals("title")){
//            filtermemberBoardDTOList = memberBoardDTOList.stream().filter(memberBoardDTO -> memberBoardDTO.getBoardTitle().contains(q))
//                    .collect(Collectors.toList());
//        }else if(type.equals("writer")){
//            filtermemberBoardDTOList = memberBoardDTOList.stream().filter(memberBoardDTO -> memberBoardDTO.getBoardWriter().contains(q))
//                    .collect(Collectors.toList());
//        }else{
//            filtermemberBoardDTOList = memberBoardDTOList;
//        }
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), filtermemberBoardDTOList.size());
//        Page<MemberBoardDTO> memberBoardDTOPage  = new PageImpl<>(filtermemberBoardDTOList.subList(start, end), pageable, filtermemberBoardDTOList.size());
//        return memberBoardDTOPage ;
//    }


    @Transactional
    public MarketProductDTO findById(Long id) {
        MarketProductEntity marketProductEntity = marketProductRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return MarketProductDTO.toDTO(marketProductEntity);
    }

    @Transactional
    public List<MarketProductDTO> findByProductWriter(String productWriter) {
        List<MarketProductEntity> marketProductEntityList = marketProductRepository.findByProductWriter(productWriter);
        List<MarketProductDTO> marketProductDTOList = new ArrayList<>();
        marketProductEntityList.forEach(marketProductEntity -> {
            marketProductDTOList.add(MarketProductDTO.toDTO(marketProductEntity));
        });
        return marketProductDTOList;
    }

    public MarketLikesDTO findByMarketLikes(String memberNickName, Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        Optional<MarketProductEntity> marketProductEntity = marketProductRepository.findById(id);
        Optional<MarketLikesEntity> optionalMarketLikes = marketLikesRepository.findByMarketProductEntityAndMemberEntity(marketProductEntity, memberEntity);
        if (optionalMarketLikes.isPresent()) {
            MarketLikesEntity marketLikesEntity = optionalMarketLikes.get();
            return MarketLikesDTO.toDTO(marketLikesEntity);
        } else {
            return null;
        }
    }

    @Transactional
    public int countMarketLikes(Long id) {
        MarketProductEntity marketProductEntity = marketProductRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return marketLikesRepository.countMarketLikes(marketProductEntity);
    }
    @Transactional
    public void addMarketLikes(String memberNickName, Long id) {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        MarketProductEntity marketProductEntity =  marketProductRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        marketLikesRepository.save(MarketLikesEntity.toSaveEntity(memberEntity, marketProductEntity));
        marketProductRepository.addMarketLikes(id);

    }

    @Transactional
    public void deleteMarketLikes(String memberNickName, Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(memberNickName);
        Optional<MarketProductEntity> marketProductEntity = marketProductRepository.findById(id);
        marketLikesRepository.deleteByMarketProductEntityAndMemberEntity(marketProductEntity, memberEntity);
        marketProductRepository.deleteMarketLikes(id);
    }

    @Transactional
    public void updateHits(Long id) {
        marketProductRepository.updateHits(id);
    }


    @Transactional
    public void CookieBoardView(Long id, HttpServletRequest req, HttpServletResponse res) {
        /* 조회수 로직 */
        Cookie oldCookie3 = null;

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("adminBoardView")) {
                    oldCookie3 = cookie;
                }
            }
        }

        if (oldCookie3 != null) {
            if (!oldCookie3.getValue().contains("[[" + id.toString() + "]]")) {
                marketProductRepository.updateHits(id);
                oldCookie3.setValue(oldCookie3.getValue() + "_[[" + id + "]]");
                oldCookie3.setPath("/");
                oldCookie3.setMaxAge(60 * 60 * 24);
                res.addCookie(oldCookie3);
            }
        } else {
            marketProductRepository.updateHits(id);
            Cookie newCookie = new Cookie("adminBoardView","[[" + id + "]]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            res.addCookie(newCookie);
        }
    }

    public void update(MarketProductDTO marketProductDTO, String memberNickName) {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        MarketProductEntity marketProductEntity = MarketProductEntity.toUpdateEntity(marketProductDTO, memberEntity);
        marketProductRepository.save(marketProductEntity);
    }

    public void delete(Long id) {
        marketProductRepository.deleteById(id);
    }
    @Transactional
    public List<MarketProductDTO> findAlltag(String productHashtag) {
        List<MarketProductEntity> marketProductEntityList = marketProductRepository.findAll();
        List<MarketProductDTO> marketProductDTOS = new ArrayList<>();

        marketProductEntityList.forEach(marketProductEntity -> {
            MarketProductDTO marketProductDTO = MarketProductDTO.toDTO(marketProductEntity);
            if(marketProductDTO.getProductHashtag().contains(productHashtag)){
                marketProductDTOS.add(marketProductDTO);
            }else{

            }
        });
        Collections.shuffle(marketProductDTOS);
        return marketProductDTOS;
    }
}