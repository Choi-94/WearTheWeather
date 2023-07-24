package com.example.weartheweather.controller;

import com.example.weartheweather.dto.MarketLikesDTO;
import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.service.MarketProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
        System.out.println("marketProductDTO = " +marketProductDTO);
        marketProductService.save(marketProductDTO, memberNickName);
        return "redirect:/market/list";
    }

    @GetMapping("/list")
    public String list(Model model,@PageableDefault(size = 15) Pageable pageable, @RequestParam(value = "type" , required = false, defaultValue = "") String type,
                        @RequestParam(value = "q" , required = false, defaultValue = "") String q) {
        Page<MarketProductDTO> marketProductDTOList = marketProductService.findAll(pageable,type,q);
        int startPage = Math.max(1,marketProductDTOList.getPageable().getPageNumber()-4);
        int endPage = Math.min(marketProductDTOList.getTotalPages(), marketProductDTOList.getPageable().getPageNumber()+4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("marketProductList", marketProductDTOList);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        return "marketPages/marketList";

    }

//    @GetMapping("/list")
//    public String findAll(Model model, @PageableDefault(size = 15) Pageable pageable, @RequestParam(value = "type", required = false, defaultValue = "") String type,
//                          @RequestParam(value = "q", required = false, defaultValue = "") String q) {
//        Page<MemberBoardDTO> memberBoardDTOList = memberBoardService.findAll(pageable,type,q);
//        int startPage = Math.max(1,memberBoardDTOList.getPageable().getPageNumber()-4);
//        int endPage = Math.min(memberBoardDTOList.getTotalPages(), memberBoardDTOList.getPageable().getPageNumber()+4);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        model.addAttribute("boardList", memberBoardDTOList);
//        model.addAttribute("type", type);
//        model.addAttribute("q", q);
//        return "/codiContestPages/boardList";
//    }



    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, HttpSession session, Model model, HttpServletRequest req, HttpServletResponse res) {
        marketProductService.CookieBoardView(id, req, res);
        String memberNickName = (String)session.getAttribute("memberNickName");
        MarketLikesDTO marketLikesDTO = marketProductService.findByMarketLikes(memberNickName, id);
        MarketProductDTO marketProductDTO = marketProductService.findById(id);
        List<MarketProductDTO> marketProductDTOListTag = marketProductService.findAlltag(marketProductDTO.getProductHashtag());
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
        model.addAttribute("marketProductDTOListTag",marketProductDTOListTag);
        System.out.println("marketProductDTOListTag = " + marketProductDTOListTag);
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

    @GetMapping("/countLikes/{id}")
    public ResponseEntity<Integer> countLikes(@PathVariable Long id) {
        int countBoardLikes = marketProductService.countMarketLikes(id);
        return new ResponseEntity<>(countBoardLikes, HttpStatus.OK);
    }

    @GetMapping("/tset")
    public String testForm() {
        return "test";
    }

    @GetMapping("/update/{id}")
    public String updateForm (@PathVariable Long id, Model model) {
        MarketProductDTO marketProductDTO = marketProductService.findById(id);
        model.addAttribute("market",marketProductDTO);
        return "/marketPages/marketUpdate";
    }

    @PutMapping("/{id}")
    public ResponseEntity marketUpdate(@RequestBody MarketProductDTO marketProductDTO, HttpSession session) {
        String memberNickName = (String)session.getAttribute("memberNickName");
        marketProductService.update(marketProductDTO, memberNickName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity marketDelete (@PathVariable Long id) {
        marketProductService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
