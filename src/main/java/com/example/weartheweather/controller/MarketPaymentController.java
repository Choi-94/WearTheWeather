package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.service.MarketProductService;
import com.example.weartheweather.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RequestMapping("/market")
@Controller
public class MarketPaymentController {
    private final MarketProductService marketProductService;
    private final MemberService memberService;

    @GetMapping("/payment/{id}")
    public String findById(@PathVariable Long id, HttpSession session,  Model model) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        String memberEmail = (String)session.getAttribute("loginEmail");
        MarketProductDTO marketProductDTO = marketProductService.findById(id);
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("MemberDTO", memberDTO);
        model.addAttribute("ProductDTO", marketProductDTO);
        model.addAttribute("memberNickName", memberNickName);
        model.addAttribute("memberEmail", memberEmail);
        return "marketPages/marketPayment";
    }

    @PostMapping ("/addWeatherPay")
    public ResponseEntity update(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        MemberDTO memberDTO1 = marketProductService.updatePay(memberNickName,memberDTO.getMemberWeatherPay());
        return new ResponseEntity<>(memberDTO1.getMemberWeatherPay(),HttpStatus.OK);

    }
}
