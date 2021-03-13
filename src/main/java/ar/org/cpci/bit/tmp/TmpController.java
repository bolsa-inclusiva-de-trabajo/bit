package ar.org.cpci.bit.tmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TmpController {

    @Autowired
    TmpRepository repo;

    @GetMapping("/")
    public String home() {
        return "home";
    }

}
