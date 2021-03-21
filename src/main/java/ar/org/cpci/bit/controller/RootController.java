package ar.org.cpci.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/international")
    public String international() {
        return "system/international";
    }

    @GetMapping("/error")
    public String error() {
        return "system/error";
    }

}
