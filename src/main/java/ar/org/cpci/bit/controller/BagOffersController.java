package ar.org.cpci.bit.controller;

import java.util.List;
import java.util.Optional;

import ar.org.cpci.bit.model.User;
import ar.org.cpci.bit.repository.UserRepository;
import ar.org.cpci.bit.security.CurrentUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ar.org.cpci.bit.model.Job;
import ar.org.cpci.bit.repository.JobRepository;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BagOffersController {

    @Autowired
    private JobRepository jobsRepository;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ApplicationContext context;


    @Query(value = "SELECT j.description FROM Job j WHERE Job.id = 1")
    public List<Job> getDescriptionNotExpiredJob()
    {
        return jobsRepository.findAll();

    }

    @GetMapping("/bagoffers")
    public String getBagOffers(Model model, @PageableDefault(size = 4) Pageable page,
                               @RequestParam("page") Optional<Integer> p,
                               @RequestParam("size") Optional<Integer> s) {



        Iterable<Job> jobs = jobsRepository.findAllActiveJobs(page);

        model.addAttribute("jobs", jobs);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CurrentUserDetails userDetail = (CurrentUserDetails)auth.getPrincipal();
        String username = userDetail.getUsername();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        int currentPage = p.orElse(0);
        int pageSize = s.orElse(4);

        List<Job> jobstotal = jobsRepository.findAll();
        int pageTotal = 1;
        if (jobstotal.size() > pageSize && pageSize > 0) {
            pageTotal = (int) Math.round(jobstotal.size() / pageSize) + 1;
        }


        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageTotal", pageTotal);


        return "bag_offers";
    }



}
