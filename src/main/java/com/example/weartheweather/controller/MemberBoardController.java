package com.example.weartheweather.controller;

import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.service.MemberBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/memberBoard")
@RequiredArgsConstructor
public class MemberBoardController {
    private final MemberBoardService memberBoardService;

    @GetMapping("/save")
    public String saveForm() {
        return "/codiContestPages/boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberBoardDTO memberBoardDTO, HttpSession session) throws IOException {
        String memberNickName =  (String)session.getAttribute("memberNickName");
        memberBoardService.save(memberBoardDTO, memberNickName);
        return "redirect:/memberBoard/list";
    }

    @GetMapping("/list")
    public String findAll() {
        return "/codiContestPages/boardList";
    }

}
