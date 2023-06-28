package com.example.weartheweather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/market")
@Controller
public class MarketController {

    @GetMapping("/save")
    public String saveForm() {
        return "marketPages/marketSave";
    }

    @GetMapping("/list")
    public String list() {
        return "marketPages/marketList";
    }

    @GetMapping("/test")
    public String test() {
        return "marketPages/test";
    }

    @GetMapping("/test2")
    public String test2() {
        return "marketPages/test2";
    }

    @GetMapping("/test3")
    public String test3() {
        return "marketPages/test3";
    }




}
