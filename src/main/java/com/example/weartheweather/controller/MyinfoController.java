package com.example.weartheweather.controller;

import com.example.weartheweather.dto.*;
import com.example.weartheweather.service.AdminBoardService;
import com.example.weartheweather.service.MemberBoardLikesService;
import com.example.weartheweather.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MyinfoController {
    private final AdminBoardService adminBoardService;
    private final MemberService memberService;
    private final MemberBoardLikesService memberBoardLikesService;

    @GetMapping("/myBoardList")
    public String myBoardListForm(Model model, HttpSession session,@PageableDefault(size = 3) Pageable pageable){
        String memberNickName = (String) session.getAttribute("memberNickName");
        MemberDTO memberDTO = memberService.findByMemberNickName(memberNickName);
        Page<MemberBoardDTO> memberBoardDTOList = adminBoardService.findByMemberBoard(memberNickName,pageable);
        int startPage = Math.max(1,memberBoardDTOList.getPageable().getPageNumber()-4);
        int endPage = Math.min(memberBoardDTOList.getTotalPages(), memberBoardDTOList.getPageable().getPageNumber()+4);
        Long totalLikes = memberBoardLikesService.totalLikes2(memberNickName);
        model.addAttribute("totalLikes", totalLikes);
        model.addAttribute("member", memberDTO);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("MemberBoardList",memberBoardDTOList);
        System.out.println("내가씀totalLikes = " + totalLikes);
        System.out.println("내가씀startPage = " + startPage);
        System.out.println("내가씀endPage = " + endPage);
        return "/myInfoPages/myBoardList";
    }

    @GetMapping("/myLikeList")
    public String myLikeListForm(Model model, HttpSession session,@PageableDefault(size = 3) Pageable pageable) {
        String memberNickName = (String) session.getAttribute("memberNickName");
        MemberDTO memberDTO = memberService.findByMemberNickName(memberNickName);
        Page<AdminBoardDTO> adminBoardDTOPage = adminBoardService.findByBoardLikesNick(memberNickName, pageable);
        int startPage = Math.max(1,adminBoardDTOPage.getPageable().getPageNumber()-4);
        int endPage = Math.min(adminBoardDTOPage.getTotalPages(), adminBoardDTOPage.getPageable().getPageNumber()+4);
        Long totalLikes = memberBoardLikesService.totalLikes2(memberNickName);
        model.addAttribute("totalLikes", totalLikes);
        model.addAttribute("member", memberDTO);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("adminBoardLikeList", adminBoardDTOPage);


        return "/adminPages/myLikeList";
    }

    @GetMapping("/myDibsList")
    public String myDibsListForm(Model model, HttpSession session,@PageableDefault(size = 3) Pageable pageable){
        String memberNickName = (String) session.getAttribute("memberNickName");
        MemberDTO memberDTO = memberService.findByMemberNickName(memberNickName);
        Page<MarketProductDTO> marketProductDTOList = adminBoardService.findByMarketProduct(memberNickName,pageable);
        int startPage = Math.max(1,marketProductDTOList.getPageable().getPageNumber()-4);
        int endPage = Math.min(marketProductDTOList.getTotalPages(), marketProductDTOList.getPageable().getPageNumber()+4);
        Long totalLikes = memberBoardLikesService.totalLikes2(memberNickName);
        model.addAttribute("totalLikes", totalLikes);
        model.addAttribute("member", memberDTO);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("MarketProduct", marketProductDTOList);
        System.out.println("찜totalLikes = " + totalLikes);
        System.out.println("찜startPage = " + startPage);
        System.out.println("찜endPage = " + endPage);
        return "/myInfoPages/myDibsList";
    }
    @GetMapping("/myTradeList")
    public String myTradeListForm(Model model, HttpSession session,@PageableDefault(size = 3) Pageable pageable){
        String memberNickName = (String) session.getAttribute("memberNickName");
        MemberDTO memberDTO = memberService.findByMemberNickName(memberNickName);
        Page<MarketPaymentDTO> marketPaymentDTOS = adminBoardService.findByMarketPayment(memberNickName,pageable);
        int startPage = Math.max(1,marketPaymentDTOS.getPageable().getPageNumber()-4);
        int endPage = Math.min(marketPaymentDTOS.getTotalPages(), marketPaymentDTOS.getPageable().getPageNumber()+4);
        Long totalLikes = memberBoardLikesService.totalLikes2(memberNickName);
        model.addAttribute("totalLikes", totalLikes);
        model.addAttribute("member", memberDTO);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("marketPaymentDTOS",marketPaymentDTOS);
        model.addAttribute("currentMember",memberDTO);
        return "/myInfoPages/myTradeList";

    }


}
