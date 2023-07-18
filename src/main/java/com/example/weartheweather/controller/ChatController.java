package com.example.weartheweather.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@Log4j2
public class ChatController {

    @GetMapping("/chat")
    public String getChat(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("memberNickName");
        System.out.println("chat param id 값 출력 : " + id);

        if (id.equals("관리자")) {
            String name = "master";
            session.setAttribute("sessionId", name);
        } else {
            String name = "guest" + session.toString().substring(session.toString().indexOf("@"));
            session.setAttribute("sessionId", name);
        }

        log.info("@ChatController, getChat()");

        return "redirect:/chat/master"; // Redirect to "/chat/master" URL
    }

    @GetMapping("/chat/master")
    public String enterChatAsMaster(HttpServletRequest request) {
        log.info("@ChatController, enterChatAsMaster()");
        return "/marketPages/chat"; // Return the view name for the chat page
    }
}
