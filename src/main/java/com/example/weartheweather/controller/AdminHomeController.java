package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.dto.AdminBoardLikesDTO;
import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.service.AdminBoardService;
import com.example.weartheweather.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminHomeController {
    private final AdminBoardService adminBoardService;
    private final MemberService memberService;

    @GetMapping("/index")
    public String adminindex(HttpSession session, Model model){
        String adminEmail = (String) session.getAttribute("loginEmail");
        MemberDTO adminDTO = memberService.findByEmail(adminEmail);
        model.addAttribute("admin", adminDTO);
        return "adminIndex";
    }

    @GetMapping("/boardList")
    public String findAll(Model model){
        List<AdminBoardDTO> adminBoardDTOList = adminBoardService.findAll();
        System.out.println("컨트롤러adminBoardDTOList = " + adminBoardDTOList);
        model.addAttribute("adminBoardList", adminBoardDTOList);
        return "/adminPages/boardList";
    }
    @GetMapping("/detail/{id}")
    public String findById(@PathVariable Long id, HttpSession session, Model model) {
        adminBoardService.updateHits(id);
        String memberNickName = (String)session.getAttribute("memberNickName");
        AdminBoardLikesDTO adminBoardLikesDTO = adminBoardService.findByBoardLikes(memberNickName, id);
        String boardLikes = "";
        if (adminBoardLikesDTO != null) {
            boardLikes = "bi-heart-fill";
        }
        int countBoardLikes = adminBoardService.countBoardLikes(id);
        AdminBoardDTO adminBoardDTO = adminBoardService.findById(id);
        model.addAttribute("boardLikes", boardLikes);
        model.addAttribute("board", adminBoardDTO);
        model.addAttribute("countBoardLikes", countBoardLikes);
        return "/adminPages/boardDetail";
    }
}
