package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.service.AdminBoardService;
import com.example.weartheweather.service.MarketProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/market")
@Controller
public class MarketController {
    private final MarketProductService marketProductService;

    @GetMapping("/save")
    public String saveForm() {
        return "marketPages/marketSave";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute MarketProductDTO marketProductDTO, HttpSession session) throws IOException {
        String memberNickName =  (String)session.getAttribute("memberNickName");
        marketProductService.save(marketProductDTO, memberNickName);
        return "redirect:/market/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<MarketProductDTO> marketProductDTOList = marketProductService.findAll();
        model.addAttribute("marketProductList", marketProductDTOList);
        return "marketPages/marketList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MarketProductDTO marketProductDTO = marketProductService.findById(id);
        model.addAttribute("ProductDTO", marketProductDTO);
        return "marketPages/marketDetail";
    }


}
