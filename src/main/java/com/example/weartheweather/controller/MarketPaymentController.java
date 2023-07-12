package com.example.weartheweather.controller;
import com.example.weartheweather.dto.MarketPaymentDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.service.MarketPaymentService;
import com.example.weartheweather.service.MarketProductService;
import com.example.weartheweather.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@RequiredArgsConstructor
@RequestMapping("/market")
@Controller
public class MarketPaymentController {
    private final MarketPaymentService marketPaymentService;
    private final MarketProductService marketProductService;
    private final MemberService memberService;

    @GetMapping("/payment/{id}")
    public String findByIdForPayment(@PathVariable Long id, HttpSession session,  Model model) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        String memberEmail = (String)session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberNickName(memberNickName);
        MarketProductDTO marketProductDTO = marketProductService.findById(id);
        model.addAttribute("MemberDTO", memberDTO);
        model.addAttribute("ProductDTO", marketProductDTO);
        model.addAttribute("memberNickName", memberNickName);
        model.addAttribute("memberEmail", memberEmail);
        return "marketPages/marketPayment";
    }

    @PostMapping ("/addWeatherPay")
    public ResponseEntity update(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        MemberDTO memberDTO1 = marketPaymentService.updatePay(memberNickName,memberDTO.getMemberWeatherPay());
        return new ResponseEntity<>(memberDTO1.getMemberWeatherPay(),HttpStatus.OK);

    }

    @PostMapping("/addTransaction")
    public ResponseEntity<Long> save(@RequestBody MarketPaymentDTO marketPaymentDTO,HttpSession session) throws IOException {
        System.out.println("marketPaymentDTO = " + marketPaymentDTO);
        String memberNickName = (String)session.getAttribute("memberNickName");

        marketPaymentService.save(marketPaymentDTO,memberNickName);
        Long id = marketPaymentDTO.getId(); // 저장된 MarketPaymentDTO의 id 값을 가져옴
        return ResponseEntity.ok(id);
    }

    @GetMapping("/PaymentSuccess/{id}")
    public String findByIdForPaymentSuccess(@PathVariable Long id, HttpSession session, Model model) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        MemberDTO memberDTO = memberService.findByMemberNickName(memberNickName);
        MarketPaymentDTO marketPaymentDTO = marketPaymentService.findById(id);
        model.addAttribute("MemberDTO", memberDTO);
        model.addAttribute("MarketPaymentDTO", marketPaymentDTO);
        model.addAttribute("memberNickName", memberNickName);
        return "marketPages/marketPaymentSuccess";
    }

}
