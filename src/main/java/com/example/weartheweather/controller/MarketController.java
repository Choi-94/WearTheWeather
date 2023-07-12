package com.example.weartheweather.controller;

import com.example.weartheweather.dto.MarketLikesDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.service.MarketProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        System.out.println("memberNickName = " + memberNickName);
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
    public String findById(@PathVariable Long id, HttpSession session, Model model, HttpServletRequest req, HttpServletResponse res) {
//        marketProductService.updateHits(id);
        marketProductService.CookieBoardView(id, req, res);
        String memberNickName = (String)session.getAttribute("memberNickName");
        MarketLikesDTO marketLikesDTO = marketProductService.findByMarketLikes(memberNickName, id);
        MarketProductDTO marketProductDTO = marketProductService.findById(id);
        List<MarketProductDTO> marketProductDTOList = marketProductService.findByProductWriter(marketProductDTO.getProductWriter());
        String marketLikes = null;
        if (marketLikesDTO != null) {
            marketLikes = "bi-heart-fill";
        }
        int countMarketLikes = marketProductService.countMarketLikes(id);
        model.addAttribute("marketLikes", marketLikes);
        model.addAttribute("market", marketProductDTO);
        model.addAttribute("countMarketLikes", countMarketLikes);
        model.addAttribute("ProductDTO", marketProductDTO);
        model.addAttribute("marketProductList", marketProductDTOList);
        return "marketPages/marketDetail";
    }

    @GetMapping("/list/{id}")
    public String findByDetailList(@PathVariable Long id, Model model) {
        MarketProductDTO marketProductDTO = marketProductService.findById(id);
        System.out.println("id = " + id + ", model = " + model);
        model.addAttribute("ProductDTO", marketProductDTO);
        List<MarketProductDTO> marketProductDTOList = marketProductService.findByProductWriter(marketProductDTO.getProductWriter());
        model.addAttribute("marketProductList", marketProductDTOList);
        return "marketPages/marketSellerList";
    }

    @GetMapping("/findByMarketLikes/{id}")
    public ResponseEntity<String> findByMarketLikes(@PathVariable Long id, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        MarketLikesDTO marketLikesDTO = marketProductService.findByMarketLikes(memberNickName, id);
        System.out.println("adminBoardLikesDTO = " + marketLikesDTO);
        if (marketLikesDTO == null) {
            marketProductService.addMarketLikes(memberNickName, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            marketProductService.deleteMarketLikes(memberNickName, id);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/tset")
    public String testForm() {
        return "test";
    }

}
