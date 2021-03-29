package ar.org.cpci.bit.controller;

import java.awt.SystemColor;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.org.cpci.bit.model.User;
import ar.org.cpci.bit.model.location.City;
import ar.org.cpci.bit.repository.CityRepository;
import ar.org.cpci.bit.repository.UserRepository;
import ar.org.cpci.bit.security.CurrentUserDetails;
import ar.org.cpci.bit.shared.Utils;

@Controller
public class UserController {

    @Autowired
    private UserRepository repositoryUser;

    @Autowired
    private CityRepository repositoryCity;

    @GetMapping("/user")
    public String getJobList(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CurrentUserDetails userDetail = (CurrentUserDetails)auth.getPrincipal();
        String username = userDetail.getUsername();
        User user = repositoryUser.findByUsername(username);
        model.addAttribute(user);
        Iterable<City> city = repositoryCity.findAll();
        model.addAttribute("cities", city);
        return "user_profile";
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

    @PostMapping("/api/user/edit")
    public String userEdit(@Valid User user, Errors errors, RedirectAttributes attrs) {
        if (!errors.hasErrors()) {
            user.setDisabled(false);
            user.setPassword(Utils.getPasswordEncoder().encode(user.getPassword()));
            repositoryUser.save(user);

            attrs.addFlashAttribute("newUsername", user.getUsername());
            return "redirect:/login";
        }
        attrs.addFlashAttribute("errors", errors);
        return "redirect:/login";
    }
    @PostMapping("/user/edit_part_profile")
    public String userPartEdit(@Valid User user, Errors errors, RedirectAttributes attrs) {
        if (!errors.hasErrors()) {
        	Optional<User> oUser = repositoryUser.findById(user.getId());
        	User dbUser = oUser.get();
            dbUser.setFirstName(user.getFirstName());
            dbUser.setLastName(user.getLastName());
//            dbUser.setEmail(user.getEmail());
            dbUser.setBirthDate(user.getBirthDate());
            dbUser.setCity(user.getCity());
            dbUser.setSkills(user.getSkills());
            dbUser.setEducation(user.getEducation());
            dbUser.setFullTime(user.getFullTime());
            dbUser.setPartTime(user.getPartTime());
            dbUser.setHomeWork(user.getHomeWork());
            dbUser.setApplyForJob(user.getApplyForJob());
            repositoryUser.save(dbUser);
            attrs.addFlashAttribute("newUsername", user.getUsername());
            return "redirect:/user";
        }
        attrs.addFlashAttribute("errors", errors);
        return "redirect:/user";
    }
    @PostMapping("/user/edit_password")
    public String userEditPassword(@Valid User user, Errors errors, RedirectAttributes attrs) {
        if (!errors.hasErrors()) {
        	Optional<User> oUser = repositoryUser.findById(user.getId());
        	User dbUser = oUser.get();
        	dbUser.setPassword(Utils.getPasswordEncoder().encode(user.getPassword()));
            repositoryUser.save(dbUser);
            return "redirect:/user";
        }
        attrs.addFlashAttribute("errors", errors);
        return "redirect:/user";
    }
    @PostMapping("/user/edit_disabled_user")
    public String userEditDisabled(@Valid User user, Errors errors, RedirectAttributes attrs) {
        if (!errors.hasErrors()) {
        	Optional<User> oUser = repositoryUser.findById(user.getId());
        	User dbUser = oUser.get();
        	dbUser.setDisabled(user.getDisabled());
            repositoryUser.save(dbUser);
            return "redirect:/user";
        }
        attrs.addFlashAttribute("errors", errors);
        return "redirect:/user";
    }
    @GetMapping("/crud_profile")
    public String crud_profile(Model model) {
        Iterable<City> city = repositoryCity.findAll();
        model.addAttribute("cities", city);
        return "crud_profile";
    }

}
