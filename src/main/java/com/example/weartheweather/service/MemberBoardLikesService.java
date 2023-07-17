package com.example.weartheweather.service;

import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.dto.MemberBoardLikesDTO;
import com.example.weartheweather.entity.MemberBoardEntity;
import com.example.weartheweather.entity.MemberBoardFileEntity;
import com.example.weartheweather.entity.MemberBoardLikesEntity;
import com.example.weartheweather.entity.MemberEntity;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MemberBoardLikesService {
    private final MemberBoardLikesRepository memberBoardLikesRepository;


    public List<MemberBoardLikesDTO> weeklyLikesList(LocalDateTime today, LocalDateTime lastWeek) {
        List<MemberBoardLikesEntity> weeklyLikesEntityList = memberBoardLikesRepository.findByCreatedAtBetween(lastWeek, today);
        List<MemberBoardLikesDTO> weeklyLikesDTOList = new ArrayList<>();
        weeklyLikesEntityList.forEach(memberBoardLikesEntity -> {
            weeklyLikesDTOList.add(MemberBoardLikesDTO.toDTO(memberBoardLikesEntity));
        });
        return weeklyLikesDTOList;
    }
}
