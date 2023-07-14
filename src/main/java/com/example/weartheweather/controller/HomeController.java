package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.dto.MarketLikesDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.service.AdminBoardService;
import com.example.weartheweather.service.MarketProductService;
import com.example.weartheweather.service.MemberBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
@RequiredArgsConstructor
@Controller
public class HomeController {
    private final MarketProductService marketProductService;
    private final MemberBoardService memberBoardService;
    private final AdminBoardService adminBoardService;

    @GetMapping("/")
    public String findByDetailList(Model model, @PageableDefault(size = 15) Pageable pageable, @RequestParam(value = "type", required = false, defaultValue = "") String type,
                                   @RequestParam(value = "q", required = false, defaultValue = "") String q) {

        List<AdminBoardDTO> adminBoardDTOList = adminBoardService.findAll();
        model.addAttribute("adminBoardDTOList", adminBoardDTOList);

        Page<MemberBoardDTO> memberBoardDTOList = memberBoardService.findAll(pageable,type,q);
        model.addAttribute("memberBoardList", memberBoardDTOList);

        List<MarketProductDTO> marketProductDTOList = marketProductService.findAll();
        model.addAttribute("marketProductList", marketProductDTOList);
        return "index";
    }
}
