package ar.org.cpci.bit.feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeatureController {

    @Autowired
    FeatureRepository repo;

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/crud_profile")
    public String crud_profile() { 
        return "crud_profile"; 
    }

}