package com.example.weartheweather.controller;

import com.example.weartheweather.dto.CommentDTO;
import com.example.weartheweather.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody CommentDTO commentDTO, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        try {
            commentService.save(memberNickName, commentDTO);
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody CommentDTO commentDTO) {
        commentService.delete(commentDTO.getId(), commentDTO.getBoardId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
