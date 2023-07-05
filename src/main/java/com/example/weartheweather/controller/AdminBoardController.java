package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.dto.AdminBoardLikesDTO;
import com.example.weartheweather.service.AdminBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/findByBoardLikes/{id}")
    public ResponseEntity<String> findByBoardLikes(@PathVariable Long id, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        AdminBoardLikesDTO adminBoardLikesDTO = adminBoardService.findByBoardLikes(memberNickName, id);
        System.out.println("adminBoardLikesDTO = " + adminBoardLikesDTO);
        if (adminBoardLikesDTO == null) {
            adminBoardService.addBoardLikes(memberNickName, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            adminBoardService.deleteBoardLikes(memberNickName, id);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


}
