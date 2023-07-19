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


import java.time.LocalDateTime;
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

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime lastWeek = today.minusWeeks(1);
        List<MemberBoardDTO> weeklyLikesList = memberBoardService.weeklyLikesList(today, lastWeek);
        if (weeklyLikesList.size() > 6) {
            weeklyLikesList = weeklyLikesList.subList(0, 6);
        }
        model.addAttribute("rankingList", weeklyLikesList);

        try {
            // 웹 페이지를 가져옵니다.
            String url = "https://www.google.com/search?q=%EC%A3%BC%EA%B0%84%EB%82%A0%EC%94%A8&sxsrf=AB5stBh6ivaxb1148YxwHqyKi_2O5u3VcA%3A1689578583893&ei=V-y0ZIeLNpTN1e8P55WN-AM&ved=0ahUKEwjHksDampWAAxWUZvUHHedKAz8Q4dUDCBA&uact=5&oq=%EC%A3%BC%EA%B0%84%EB%82%A0%EC%94%A8&gs_lp=Egxnd3Mtd2l6LXNlcnAiDOyjvOqwhOuCoOyUqDIPECMYigUYJxidAhhGGIACMgcQIxiKBRgnMgcQABiKBRhDMgsQABiABBixAxiDATIHEAAYigUYQzIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAESKYSUJ8KWP4QcAR4AJABApgBsQOgAawQqgEJMC42LjIuMS4xuAEDyAEA-AEBwgIKEAAYRxjWBBiwA8ICCBAAGIAEGLEDwgIKECMYigUYJxidAsICERAuGIAEGLEDGIMBGMcBGNEDwgILEC4YgAQYsQMYgwHiAwQYACBBiAYBkAYK&sclient=gws-wiz-serp";
            Document doc = Jsoup.connect(url).get();

            // class="num_quot up"를 가진 요소를 찾습니다.
//            Element day0 = doc.select(".wob_df").get(0); // day0 값 가져오기
            Element D_day0 = doc.select("#dimg_1").get(0); // 날씨
            Element D_day1 = doc.select("#dimg_3").get(0); // 날씨
            Element D_day2 = doc.select("#dimg_5").get(0); // 날씨
            Element D_day3 = doc.select("#dimg_7").get(0); // 날씨
            Element D_day4 = doc.select("#dimg_9").get(0); // 날씨
            Element D_day5 = doc.select("#dimg_11").get(0); // 날씨
            Element D_day6 = doc.select("#dimg_13").get(0); // 날씨
//            String value = day0.text().substring(0, 1); // 앞에서부터 1번째 문자열까지 추출
//            String value1 = day0.text().substring(2, 4); // 최저온도
//            String value2 = day0.text().substring(8, 10); // 최고온도

            String D0 = D_day0.attr("alt");
            String D1 = D_day1.attr("alt");
            String D2 = D_day2.attr("alt");
            String D3 = D_day3.attr("alt");
            String D4 = D_day4.attr("alt");
            String D5 = D_day5.attr("alt");
            String D6 = D_day6.attr("alt");





            // 요소의 텍스트를 모델에 추가합니다.
            model.addAttribute("D0", D0);
            model.addAttribute("D1", D1);
            model.addAttribute("D2", D2);
            model.addAttribute("D3", D3);
            model.addAttribute("D4", D4);
            model.addAttribute("D5", D5);
            model.addAttribute("D6", D6);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }



}