package ar.org.cpci.bit.controller;

import java.util.*;

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
import org.thymeleaf.util.TextUtils;

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

        User user = findUser();
        model.addAttribute("user", user);

        int currentPage = p.orElse(0);
        int pageSize = s.orElse(4);
        int pageTotal = findPageTotal(pageSize);


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


        Iterable<Job> jobs = jobsRepository.findTextFilteredJobs(page, filterText.toLowerCase(Locale.ROOT));
        model.addAttribute("jobs", jobs);

        User user = findUser();

        model.addAttribute("user", user);

        int currentPage = p.orElse(0);
        int pageSize = s.orElse(4);
        int pageTotal = findPageTotal(pageSize);

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

        User user = findUser();
        model.addAttribute("user", user);

        //cargo la ciudad para el filtro si es necesario
        long fCity = 0;
        String sCity = user.getCity().getName();
        if (filterCity) {
            fCity = user.getCity().getId();

        }

        Iterable<Job> jobs = jobsRepository.findCityFilteredJobs(page, fCity);
        model.addAttribute("jobs", jobs);

        int currentPage = p.orElse(0);
        int pageSize = s.orElse(100);
        int pageTotal = findPageTotal(pageSize);


        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("filterType", "city");
        model.addAttribute("filterContent", sCity);
        return "bag_offers";
    }
    
    @PostMapping("/api/job/edit")
    public String jobEdit(@Valid Job job, BindingResult bindingResult, RedirectAttributes attrs) {
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
        return "redirect:/bagoffers"; 
    }

    private User findUser() {
        //busco el usuario
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CurrentUserDetails userDetail = (CurrentUserDetails)auth.getPrincipal();
        String username = userDetail.getUsername();
        User user = userRepository.findByUsername(username);
        return user;
    }

    private int findPageTotal(int pageSize) {
        List<Job> jobstotal = jobsRepository.findAll();
        int pageTotal = 1;

        if (jobstotal.size() > pageSize && pageSize > 0) {
            double factor = (double)jobstotal.size() / (double)pageSize;
            if (factor > 1) {
                pageTotal = ((int)factor) + 1;
            }
        }
        return pageTotal;
    }

    private int findPageTotal(int pageSize, List<User> matches) {
        int pageTotal = 1;

        if (matches.size() > pageSize && pageSize > 0) {
            double factor = (double)matches.size() / (double)pageSize;
            if (factor > 1) {
                pageTotal = ((int)factor) + 1;
            }
        }
        return pageTotal;
    }

    @GetMapping("/bagoffers/my_offers/{filterOffers}")
    public String getBagMyOffersFiltered(Model model, @PageableDefault(size = 100) Pageable page,
                                           @RequestParam("page") Optional<Integer> p,
                                           @RequestParam("size") Optional<Integer> s,
                                           @PathVariable Boolean filterOffers) {
        User user = findUser();
        model.addAttribute("user", user);

        Iterable<Job> jobs = jobsRepository.findMyOffersFilteredJobs(page, user.getId());
        model.addAttribute("jobs", jobs);

        int currentPage = p.orElse(0);
        int pageSize = s.orElse(100);
        int pageTotal = findPageTotal(pageSize);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("filterType", "my_offers");
        model.addAttribute("filterContent", "SOLO MIS OFERTAS LABORALES");
        return "bag_offers";
    }

    @GetMapping("/bagoffers/fullparthome/{filterType}")
    public String getBagFullPartHomeFiltered(Model model, @PageableDefault(size = 100) Pageable page,
                                         @RequestParam("page") Optional<Integer> p,
                                         @RequestParam("size") Optional<Integer> s,
                                         @PathVariable String filterType) {
        User user = findUser();
        model.addAttribute("user", user);
        Iterable<Job> jobs = null;
        String filterContent = "";

            if (filterType.equals("full")) {
                jobs = jobsRepository.findByFullTimeTrue(page);
                filterContent = "OFERTAS LABORALES TIEMPO COMPLETO";
            } else {
                if (filterType.equals("part")) {
                    jobs = jobsRepository.findByPartTimeTrue(page);
                    filterContent = "OFERTAS LABORALES MEDIO TIEMPO";
                } else {
                    if (filterType.equals("home")) {
                        jobs = jobsRepository.findByHomeWorkTrue(page);
                        filterContent = "OFERTAS LABORALES REMOTAS";
                    } else {
                        jobs = jobsRepository.findAllActiveJobs(page);
                        filterContent = "";
                    }
                }
            }

            model.addAttribute("jobs", jobs);

            int currentPage = p.orElse(0);
            int pageSize = s.orElse(100);
            int pageTotal = findPageTotal(pageSize);

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pageTotal", pageTotal);
            model.addAttribute("filterType", filterType);
            model.addAttribute("filterContent", filterContent);
            return "bag_offers";



    }

    @GetMapping("/matches")
    public String getMatch(Model model, @PageableDefault(size = 4) Pageable page,
                                             @RequestParam("page") Optional<Integer> p,
                                             @RequestParam("size") Optional<Integer> s) {
        User user = findUser();
        model.addAttribute("user", user);

        List<User> matches = new ArrayList<>();




        /* PROCESO PARA ENCONTRAR COINCIDENCIAS DEL LADO DEL EMPLEADOR */

        Iterable<User> postulants = userRepository.findByApplyForJobTrueAndDisabledFalse(page);
        for(Job job: user.getCreatedJobs() ) {
            for(User postulant: postulants ) {
                //solo si no soy yo.
                if (postulant.getId() != user.getId()) {
                    if (postulant.containsApplyJob(job)) {
                        if (user.containsContact(postulant)) {
                            if (!postulant.getApplyForJob()) {
                                matches.add(postulant);
                            }
                        }
                    }
                }
            }
        }

        /* PROCESO PARA ENCONTRAR COINCIDENCIAS DEL LADO DEL POSTULANTE */

        for(Job apply: user.getApplyJobs() ) {
            if (apply.getOwner().containsContact(user)) {
                if (apply.getOwner().getApplyForJob()) {
                    matches.add(apply.getOwner());
                }
            }
        }


        model.addAttribute("users", (Iterable<User>)matches);

        int currentPage = p.orElse(0);
        int pageSize = s.orElse(4);
        int pageTotal = findPageTotal(pageSize, matches);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageTotal", pageTotal);

        return "matches";

    }
}
