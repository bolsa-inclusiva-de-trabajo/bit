package ar.org.cpci.bit.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ar.org.cpci.bit.model.User;
import ar.org.cpci.bit.model.location.City;
import ar.org.cpci.bit.repository.CityRepository;
import ar.org.cpci.bit.repository.UserRepository;
import ar.org.cpci.bit.shared.Utils;

@Controller
public class UserController {

    @Autowired
    private UserRepository repositoryUser;
    
    @Autowired
    private CityRepository repositoryCity;
    
    @Autowired
    private ApplicationContext context;

    @GetMapping("/user")
    public String getJobList(Model model, @PageableDefault(size = 5) Pageable page) {
        Iterable<User> users = repositoryUser.findAll(page);
        model.addAttribute("jobs", users);
        return "user_list";
    }

    @GetMapping("/user/{id}")
    public String getUserDetail(@PathVariable Long id, Model model) {
        Optional<User> user = repositoryUser.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        }
        return "user_detail";
    }


    @GetMapping("/user/edit")
    public String jobEdit(User user) {
        return "user_edit";
    }

    @PostMapping("/user/edit")
    public String userEdit(@Valid User user, BindingResult bindingResult) {    	
        if (!bindingResult.hasErrors()) {
        	user.setApplyForJob(true);    	
        	user.setPassword(Utils.getPasswordEncoder().encode(user.getPassword()));
        	repositoryUser.save(user);
            return "redirect:/bagoffers";
        }
        return "user_edit";
    }
    @GetMapping("/crud_profile")
    public String crud_profile(Model model) {
        Iterable<City> city = repositoryCity.findAll();
        model.addAttribute("cities", city);
        return "crud_profile";
    }

}
