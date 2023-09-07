package com.todo.securitySetting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main")
public class MainController {
    @RequestMapping("/dashboard")
    public String dashboard(@RequestParam(required=false) String errorCode, Model model) {
        if(errorCode != null){
            model.addAttribute("errorCode", errorCode);
        }
        return "dashboard";
    }
}
