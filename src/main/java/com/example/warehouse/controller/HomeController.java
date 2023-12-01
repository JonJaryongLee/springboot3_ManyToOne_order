package com.example.warehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * "GET /" 처리. 첫 페이지 반환.
     *
     * @return "home" 반환할 HTML 파일 이름
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
