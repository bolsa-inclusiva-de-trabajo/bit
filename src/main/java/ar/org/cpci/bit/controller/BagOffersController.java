package ar.org.cpci.bit.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import ar.org.cpci.bit.model.Job;
import ar.org.cpci.bit.repository.JobRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BagOffersController {

    @Autowired
    private JobRepository jobsRepository;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ApplicationContext context;


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
            double factor = (double)jobstotal.size() / (double)pageSize;
            if (factor > 1) {
                pageTotal = ((int)factor) + 1;
            }
        }

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("filterType", "");
        model.addAttribute("filterContent", "");
        return "bag_offers";
    }


    @GetMapping("/bagoffers/text/{filterText}")
    public String getBagOffersTextFiltered(Model model, @PageableDefault(size = 4) Pageable page,
                                       @RequestParam("page") Optional<Integer> p,
                                       @RequestParam("size") Optional<Integer> s,
                                       @PathVariable String filterText) {


        Iterable<Job> jobs = jobsRepository.findTextFilteredJobs(page, filterText);
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
            double factor = (double)jobstotal.size() / (double)pageSize;
            if (factor > 1) {
                pageTotal = ((int)factor) + 1;
            }
        }

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("filterType", "text");
        model.addAttribute("filterContent", filterText);

        return "bag_offers";
    }

    @GetMapping("/bagoffers/city/{filterCity}")
    public String getBagOffersCityFiltered(Model model, @PageableDefault(size = 100) Pageable page,
                                       @RequestParam("page") Optional<Integer> p,
                                       @RequestParam("size") Optional<Integer> s,
                                       @PathVariable Boolean filterCity) {

        //busco el usuario
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CurrentUserDetails userDetail = (CurrentUserDetails)auth.getPrincipal();
        String username = userDetail.getUsername();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        //cargo la ciudad para el filtro si es necesario
        long fCity = 0;

        if (filterCity) {
            fCity = user.getCity().getId();

        }

        Iterable<Job> jobs = jobsRepository.findCityFilteredJobs(page, fCity);
        model.addAttribute("jobs", jobs);

        int currentPage = p.orElse(0);
        int pageSize = s.orElse(100);

        List<Job> jobstotal = jobsRepository.findAll();
        int pageTotal = 1;

        if (jobstotal.size() > pageSize && pageSize > 0) {
            double factor = (double)jobstotal.size() / (double)pageSize;
            if (factor > 1) {
                pageTotal = ((int)factor) + 1;
            }
        }

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("filterType", "city");
        model.addAttribute("filterContent", "");
        return "bag_offers";
    }
    @PostMapping("/api/job/edit")
    public String jobEdit(@Valid Job job, BindingResult bindingResult, RedirectAttributes attrs) {
    	System.out.println(job.getTitle()+job.getDescription()+job.getExpiration());
        if (!bindingResult.hasErrors()) {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CurrentUserDetails userDetail = (CurrentUserDetails)auth.getPrincipal();
            String username = userDetail.getUsername();
            User user = userRepository.findByUsername(username);
            job.setOwner(user);
            job.setDisabled(false);
            jobsRepository.save(job); 
            return "redirect:/bagoffers"; 
        }
        attrs.addFlashAttribute("error_create_offer", 1);
        return "redirect:/bagoffers"; 
    }

}
