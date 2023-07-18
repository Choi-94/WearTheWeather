package com.example.weartheweather.service;

import com.example.weartheweather.dto.CommentDTO;
import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.entity.CommentEntity;
import com.example.weartheweather.entity.MemberBoardEntity;
import com.example.weartheweather.entity.MemberEntity;
import com.example.weartheweather.repository.CommentRepository;
import com.example.weartheweather.repository.MemberBoardRepository;
import com.example.weartheweather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberBoardRepository memberBoardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<CommentDTO> findAll(Long id) {
        MemberBoardEntity memberBoardEntity = memberBoardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        List<CommentEntity> commentEntityList = commentRepository.findByMemberBoardEntityOrderByIdDesc(memberBoardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentEntityList.forEach(commentEntity -> {
            commentDTOList.add(CommentDTO.toDTOpoint(commentEntity));
        });
        return commentDTOList;
    }

    public void save(String memberNickName, CommentDTO commentDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberNickName(memberNickName).orElseThrow(() -> new NoSuchElementException());
        MemberBoardEntity memberBoardEntity = memberBoardRepository.findById(commentDTO.getBoardId()).orElseThrow(() -> new NoSuchElementException());
        CommentEntity commentEntity = CommentEntity.toSaveEntity(memberEntity ,memberBoardEntity, commentDTO);
        commentRepository.save(commentEntity);
    }

    @Transactional
    public void delete(Long id, Long boardId) {
        commentRepository.deleteByIdAndMemberBoardEntityId(id, boardId);
    }

    public List<MemberDTO> findByMyPoint(Long id) {
        List<CommentDTO> commentDTOList = this.findAll(id);
        List<MemberDTO> memberDTOList = new ArrayList<>();
        commentDTOList.forEach(commentDTO -> {
            Optional<MemberEntity> memberEntity = memberRepository.findByMemberNickName(commentDTO.getCommentWriter());
            memberDTOList.add(MemberDTO.tofindAll(memberEntity.get()));
        });
        return memberDTOList;
    }
}
