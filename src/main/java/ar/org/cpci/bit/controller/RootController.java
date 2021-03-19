package ar.org.cpci.bit.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/international")
    public String getInternationalPage() {
        return "system/international";
    }

    @GetMapping("/current_locale")
    public @ResponseBody String locale(Locale locale) {
        return locale.toString();
    }

    @GetMapping("/error")
    public String errorpage() {
        return "system/error";
    }

}
