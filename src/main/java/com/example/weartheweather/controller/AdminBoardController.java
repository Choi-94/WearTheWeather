package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.service.AdminBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "/weatherCodiPages/boardList";
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<AdminBoardDTO> adminBoardDTOList = adminBoardService.findAll();
        model.addAttribute("adminBoardList", adminBoardDTOList);
        return "/weatherCodiPages/boardList";
    }


}
