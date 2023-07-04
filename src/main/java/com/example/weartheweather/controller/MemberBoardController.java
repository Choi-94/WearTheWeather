package com.example.weartheweather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/memberBoard")
public class MemberBoardController {

    @GetMapping("/save")
    public String saveForm() {
        return "/codiContestPages/boardSave";
    }

    @GetMapping("/list")
    public String findAll() {
        return "/codiContestPages/boardList";
    }

}
