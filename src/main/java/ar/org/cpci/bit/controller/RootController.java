package ar.org.cpci.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ar.org.cpci.bit.model.location.City;
import ar.org.cpci.bit.repository.CityRepository;
import ar.org.cpci.bit.security.CurrentUserDetails;
import ar.org.cpci.bit.shared.Utils;

@Controller
public class RootController {

    @Autowired
    private CityRepository repositoryCity;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("userTargetUrl", Utils.getUserTargetUrl());
        model.addAttribute("cities", repositoryCity.findAll());
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userTargetUrl", Utils.getUserTargetUrl());
        model.addAttribute("cities", repositoryCity.findAll());
        return "system/login";
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
