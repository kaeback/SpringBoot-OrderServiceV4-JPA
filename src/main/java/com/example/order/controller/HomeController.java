package com.example.order.controller;

import com.example.order.config.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserInfo userInfo, Model model) {
        model.addAttribute("loginUser", userInfo);
        return "index";
    }

    @GetMapping("admin")
    public String adminHome(@AuthenticationPrincipal UserInfo userInfo,
                            Model model) {
        model.addAttribute("adminUser", userInfo);
        return "admin/index";
    }
}