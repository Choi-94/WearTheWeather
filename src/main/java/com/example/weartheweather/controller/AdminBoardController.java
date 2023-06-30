package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.service.AdminBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adminBoard")
public class AdminBoardController {
    private final AdminBoardService adminBoardService;
    @GetMapping("/save")
    public String saveForm() {
        return "/adminPages/boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AdminBoardDTO adminBoardDTO) throws IOException {
        adminBoardService.save(adminBoardDTO);
        return "redirect:/adminBoard/list";
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<AdminBoardDTO> adminBoardDTOList = adminBoardService.findAll();
        System.out.println("컨트롤러adminBoardDTOList = " + adminBoardDTOList);
        model.addAttribute("adminBoardList", adminBoardDTOList);
        return "/weatherCodiPages/boardList";
    }

    @GetMapping("/detail/{id}")
    public String findById(@PathVariable Long id, Model model) {
        adminBoardService.updateHits(id);
        int countBoardLikes = adminBoardService.countBoardLikes(id);
        AdminBoardDTO adminBoardDTO = adminBoardService.findById(id);
        model.addAttribute("board", adminBoardDTO);
        model.addAttribute("boardLikes", countBoardLikes);
        return "/weatherCodiPages/boardDetail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        AdminBoardDTO adminBoardDTO = adminBoardService.findById(id);
        model.addAttribute("board", adminBoardDTO);
        return "/weatherCodiPages/boardUpdate";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute AdminBoardDTO adminBoardDTO) {
        adminBoardService.update(adminBoardDTO);
        return "redirect:/adminBoard/list";
    }

    // 좋아요 취소
//   @GetMapping("/deleteBoardLikes/{id}")
//    public String deleteBoardLikes(@PathVariable Long id) {
//        adminBoardService.
//   }

    @GetMapping("/addBoardLikes/{id}")
    public String addBoardLikes(@PathVariable Long id, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        adminBoardService.addBoardLikes(memberNickName, id);
        return "redirect:/adminBoard/detail/" + id;
    }

}
