package com.example.weartheweather.controller;

import com.example.weartheweather.dto.MarketLikesDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.service.MarketProductService;
import com.example.weartheweather.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/market")
@Controller
public class MarketPaymentController {
    private final MarketProductService marketProductService;

    @GetMapping("/payment/{id}")
    public String findById(@PathVariable Long id, HttpSession session, Model model) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        String memberEmail = (String)session.getAttribute("memberEmail");
        Long memberWeatherPay = (Long)session.getAttribute("memberWeatherPay");
        MarketLikesDTO marketLikesDTO = marketProductService.findByMarketLikes(memberNickName, id);
        MarketProductDTO marketProductDTO = marketProductService.findById(id);
        List<MarketProductDTO> marketProductDTOList = marketProductService.findByProductWriter(marketProductDTO.getProductWriter());
        model.addAttribute("market", marketProductDTO);
        model.addAttribute("ProductDTO", marketProductDTO);
        model.addAttribute("MemberNickName", memberNickName);
        model.addAttribute("memberWeatherPay", memberWeatherPay);
        model.addAttribute("marketProductList", marketProductDTOList);
        return "marketPages/marketPayment";
    }
}
