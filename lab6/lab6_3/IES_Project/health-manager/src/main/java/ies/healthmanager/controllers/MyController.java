package ies.healthmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/index")
    public String getIndex() {
        return "redirect:/";
    }

    @GetMapping("/home")
    public String getHome() {
        return "redirect:/";
    }
}