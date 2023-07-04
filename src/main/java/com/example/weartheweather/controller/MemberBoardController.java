package com.example.weartheweather.controller;

import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.service.MemberBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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
    public String findAll(Model model) {
        List<MemberBoardDTO> memberBoardDTOList = memberBoardService.findAll();
        model.addAttribute("boardList", memberBoardDTOList);
        return "/codiContestPages/boardList";
    }

    @GetMapping("/detail/{id}")
    public String findById(@PathVariable Long id, Model model) {
        return "/codiContestPages/boardDetail";
    }

}
