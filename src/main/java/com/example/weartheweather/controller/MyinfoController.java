package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.service.AdminBoardService;
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

    @GetMapping("/myBoardList")
    public String myBoardListForm(Model model, HttpSession session,@PageableDefault(size = 3) Pageable pageable){
        String memberNickName = (String) session.getAttribute("memberNickName");
        Page<MemberBoardDTO> memberBoardDTOList = adminBoardService.findByMemberBoard(memberNickName,pageable);
        int startPage = Math.max(1,memberBoardDTOList.getPageable().getPageNumber()-4);
        int endPage = Math.min(memberBoardDTOList.getTotalPages(), memberBoardDTOList.getPageable().getPageNumber()+4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("MemberBoardList",memberBoardDTOList);
        return "/myInfoPages/myBoardList";
    }

    @GetMapping("/myLikeList")
    public String myLikeListForm(Model model, HttpSession session,@PageableDefault(size = 3) Pageable pageable) {
        String memberNickName = (String) session.getAttribute("memberNickName");
        Page<AdminBoardDTO> adminBoardDTOPage = adminBoardService.findByBoardLikesNick(memberNickName, pageable);
        System.out.println("adminBoardDTOPage = " + adminBoardDTOPage);
        int startPage = Math.max(1,adminBoardDTOPage.getPageable().getPageNumber()-4);
        int endPage = Math.min(adminBoardDTOPage.getTotalPages(), adminBoardDTOPage.getPageable().getPageNumber()+4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("adminBoardLikeList", adminBoardDTOPage);


        return "/adminPages/myLikeList";
    }

    @GetMapping("/myDibsList")
    public String myDibsListForm(Model model, HttpSession session,@PageableDefault(size = 3) Pageable pageable){
        String memberNickName = (String) session.getAttribute("memberNickName");
        Page<MarketProductDTO> marketProductDTOList = adminBoardService.findByMarketProduct(memberNickName,pageable);
        int startPage = Math.max(1,marketProductDTOList.getPageable().getPageNumber()-4);
        int endPage = Math.min(marketProductDTOList.getTotalPages(), marketProductDTOList.getPageable().getPageNumber()+4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("MarketProduct", marketProductDTOList);
        return "/myInfoPages/myDibsList";

    }


}
