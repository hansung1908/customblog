package com.hansung.customblog.controller;

import com.hansung.customblog.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal PrincipalDetail principalDetail) {
        return "index";
    }
}
