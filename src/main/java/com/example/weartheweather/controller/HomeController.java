package com.example.weartheweather.controller;

import com.example.weartheweather.dto.AdminBoardDTO;

import com.example.weartheweather.dto.MarketProductDTO;
import com.example.weartheweather.dto.MemberBoardDTO;
import com.example.weartheweather.service.AdminBoardService;
import com.example.weartheweather.service.MarketProductService;
import com.example.weartheweather.service.MemberBoardService;
import com.example.weartheweather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@RequiredArgsConstructor
@Controller
public class HomeController {
    private final MarketProductService marketProductService;
    private final MemberBoardService memberBoardService;
    private final AdminBoardService adminBoardService;
    private final WeatherService weatherService;


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
            // 날씨 값 가져오기
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

            String D0 = convertWeather(D_day0.attr("alt"));
            String D1 = convertWeather(D_day1.attr("alt"));
            String D2 = convertWeather(D_day2.attr("alt"));
            String D3 = convertWeather(D_day3.attr("alt"));
            String D4 = convertWeather(D_day4.attr("alt"));
            String D5 = convertWeather(D_day5.attr("alt"));
            String D6 = convertWeather(D_day6.attr("alt"));


//            각 값을 가져오기 도영
            List<AdminBoardDTO> adminBoardDTOSd0 = weatherService.findWeather(D0);
            List<AdminBoardDTO> adminBoardDTOSd1 = weatherService.findWeather(D1);
            List<AdminBoardDTO> adminBoardDTOSd2 = weatherService.findWeather(D2);
            List<AdminBoardDTO> adminBoardDTOSd3 = weatherService.findWeather(D3);
            List<AdminBoardDTO> adminBoardDTOSd4 = weatherService.findWeather(D4);
            List<AdminBoardDTO> adminBoardDTOSd5 = weatherService.findWeather(D5);
            List<AdminBoardDTO> adminBoardDTOSd6 = weatherService.findWeather(D6);
            // 요소의 텍스트를 모델에 추가합니다.
            model.addAttribute("D0", adminBoardDTOSd0);
            model.addAttribute("D1", adminBoardDTOSd1);
            model.addAttribute("D2", adminBoardDTOSd2);
            model.addAttribute("D3", adminBoardDTOSd3);
            model.addAttribute("D4", adminBoardDTOSd4);
            model.addAttribute("D5", adminBoardDTOSd5);
            model.addAttribute("D6", adminBoardDTOSd6);




            // 요일데이터 추출
            Element day0 = doc.select(".wob_df").get(0);
            Element day1 = doc.select(".wob_df").get(1);
            Element day2 = doc.select(".wob_df").get(2);
            Element day3 = doc.select(".wob_df").get(3);
            Element day4 = doc.select(".wob_df").get(4);
            Element day5 = doc.select(".wob_df").get(5);
            Element day6 = doc.select(".wob_df").get(6);


            String  D0_0 = day0.text().substring(0, 1);
            String  D0_1 = day1.text().substring(0, 1);
            String  D0_2 = day2.text().substring(0, 1);
            String  D0_3 = day3.text().substring(0, 1);
            String  D0_4 = day4.text().substring(0, 1);
            String  D0_5 = day5.text().substring(0, 1);
            String  D0_6 = day6.text().substring(0, 1);


            model.addAttribute("D0_0", D0_0);
            model.addAttribute("D0_1", D0_1);
            model.addAttribute("D0_2", D0_2);
            model.addAttribute("D0_3", D0_3);
            model.addAttribute("D0_4", D0_4);
            model.addAttribute("D0_5", D0_5);
            model.addAttribute("D0_6", D0_6);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }


    private String convertWeather(String weather) {
        if (weather.contains("맑음")) {
            return "맑음";
        } else if (weather.contains("구름")) {
            return "흐림";
        } else if (weather.contains("소나기") || weather.contains("뇌우") || weather.contains("강우")) {
            return "비";
        } else if (weather.contains("눈")) {
            return "눈";
        } else {
            return weather; // 기타 경우에는 원래의 값을 그대로 반환
        }
    }


}