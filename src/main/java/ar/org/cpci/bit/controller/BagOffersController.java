package ar.org.cpci.bit.controller;

import java.util.Optional;

import javax.validation.Valid;

import ar.org.cpci.bit.model.User;
import ar.org.cpci.bit.repository.UserRepository;
import ar.org.cpci.bit.security.CurrentUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ar.org.cpci.bit.model.Job;
import ar.org.cpci.bit.repository.JobRepository;

@Controller
public class BagOffersController {

    @Autowired
    private JobRepository jobsRepository;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ApplicationContext context;

    @GetMapping("/bagoffers")
    public String getBagOffers(Model model, @PageableDefault(size = 5) Pageable page) {
        Iterable<Job> jobs = jobsRepository.findAll(page);
        model.addAttribute("jobs", jobs);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        CurrentUserDetails p = (CurrentUserDetails)auth.getPrincipal();


        String username1 = p.getUsername();
        User user = userRepository.findByUsername(username1);
        model.addAttribute("user", user);

        return "bag_offers";
    }



}
