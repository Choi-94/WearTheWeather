package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;

import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.service.AdminBoardService;
import com.example.weartheweather.service.MarketProductService;
import com.example.weartheweather.service.MemberBoardService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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

        Page<MarketProductDTO> marketProductDTOList = marketProductService.findAll(pageable, type, q);
        model.addAttribute("marketProductList", marketProductDTOList);

        try {
            // 웹 페이지를 가져옵니다.
            String url = "https://www.google.com/search?q=%EC%A3%BC%EA%B0%84%EB%82%A0%EC%94%A8&sxsrf=AB5stBh6ivaxb1148YxwHqyKi_2O5u3VcA%3A1689578583893&ei=V-y0ZIeLNpTN1e8P55WN-AM&ved=0ahUKEwjHksDampWAAxWUZvUHHedKAz8Q4dUDCBA&uact=5&oq=%EC%A3%BC%EA%B0%84%EB%82%A0%EC%94%A8&gs_lp=Egxnd3Mtd2l6LXNlcnAiDOyjvOqwhOuCoOyUqDIPECMYigUYJxidAhhGGIACMgcQIxiKBRgnMgcQABiKBRhDMgsQABiABBixAxiDATIHEAAYigUYQzIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAESKYSUJ8KWP4QcAR4AJABApgBsQOgAawQqgEJMC42LjIuMS4xuAEDyAEA-AEBwgIKEAAYRxjWBBiwA8ICCBAAGIAEGLEDwgIKECMYigUYJxidAsICERAuGIAEGLEDGIMBGMcBGNEDwgILEC4YgAQYsQMYgwHiAwQYACBBiAYBkAYK&sclient=gws-wiz-serp";
            Document doc = Jsoup.connect(url).get();

            // class="num_quot up"를 가진 요소를 찾습니다.
//            Element day0 = doc.select(".wob_df").get(0); // day0 값 가져오기
            Element day11 = doc.select("#dimg_9").get(0); // 날씨
            Element day13 = doc.select("#dimg_11").get(0); // 날씨
            Element day15 = doc.select("#dimg_13").get(0); // 날씨
            Element day17 = doc.select("#dimg_15").get(0); // 날씨
//            String value = day0.text().substring(0, 1); // 앞에서부터 1번째 문자열까지 추출
//            String value1 = day0.text().substring(2, 4); // 최저온도
//            String value2 = day0.text().substring(8, 10); // 최고온도
            String altValue1 = day11.attr("alt");
            String altValue2 = day13.attr("alt");
            String altValue3 = day15.attr("alt");
            String altValue4 = day17.attr("alt");




            // 요소의 텍스트를 모델에 추가합니다.
//            model.addAttribute("day0", value);
//            model.addAttribute("day0_temp0", value1);
//            model.addAttribute("day0_temp1", value2);
            model.addAttribute("day11", altValue1);
            model.addAttribute("day13", altValue2);
            model.addAttribute("day15", altValue3);
            model.addAttribute("day17", altValue4);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }



}