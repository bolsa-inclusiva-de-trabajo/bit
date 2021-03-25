package ar.org.cpci.bit.controller;

import java.util.Optional;

import javax.validation.Valid;

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
import ar.org.cpci.bit.model.User;
import ar.org.cpci.bit.repository.JobRepository;
import ar.org.cpci.bit.repository.UserRepository;
import ar.org.cpci.bit.security.CurrentUserDetails;

@Controller
public class JobController {

    @Autowired
    private JobRepository repositoryJob;

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/job")
    public String getJobList(Model model, @PageableDefault(size = 5) Pageable page) {
        Iterable<Job> jobs = repositoryJob.findAll(page);
        model.addAttribute("jobs", jobs);
        return "job_list";
    }

    @GetMapping("/job/{id}")
    public String getJobDetail(@PathVariable Long id, Model model) {
        Optional<Job> job = repositoryJob.findById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get());
        }
        return "job_detail";
    }

    @GetMapping("/job/edit")
    public String jobEdit(Job job) {
        return "job_edit";
    }

    @PostMapping("/api/job/edit")
    public String jobEdit(@Valid Job job, BindingResult bindingResult) {
    	System.out.println(job.getTitle()+job.getDescription()+job.getExpiration());
        if (!bindingResult.hasErrors()) {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CurrentUserDetails userDetail = (CurrentUserDetails)auth.getPrincipal();
            String username = userDetail.getUsername();
            User user = userRepository.findByUsername(username);
            job.setOwner(user);
            job.setDisabled(false);
            repositoryJob.save(job); 
            return "redirect:/job"; 
        }
        return "crud_offers";
    }
    @GetMapping("/crud_offers")
    public String crudOffers( ) {
        return "crud_offers";
    }

}
