package com.example.weartheweather.controller;

import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @GetMapping("/memberLogin")
    public String loginForm(){
        return "/memberPages/memberLogin";
    }

    @GetMapping("/memberSave")
    public String saveForm(){
        return "/memberPages/memberSave";
    }


    @PostMapping("/memberSave")
    public String save(@ModelAttribute MemberDTO memberDTO, Model model){
        memberService.save(memberDTO);
        System.out.println("memberDTO = " + memberDTO);
        return "/memberPages/memberLogin";

    }

    @PostMapping("/emailcheck")
    public ResponseEntity emailcheck(@ModelAttribute MemberDTO memberDTO){

        int res = memberService.emailcheck(memberDTO.getMemberEmail());
        System.out.println("res"+res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PostMapping("/nickcheck")
    public ResponseEntity nickcheck(@ModelAttribute MemberDTO memberDTO){

        int res = memberService.nickcheck(memberDTO.getMemberNickName());
        System.out.println("res"+res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
