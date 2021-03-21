package ar.org.cpci.bit.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ar.org.cpci.bit.security.CurrentUserDetails;
import ar.org.cpci.bit.shared.Utils;

@Controller
public class RootController {

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            CurrentUserDetails user = (CurrentUserDetails) auth.getPrincipal();
            model.addAttribute("bagUrl", Utils.getAuthSuccessUrl(user.havePublishedJobs()));
        } catch (ClassCastException e) {
            model.addAttribute("bagUrl", null);
        }
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
