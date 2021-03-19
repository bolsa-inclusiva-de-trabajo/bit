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

import ar.org.cpci.bit.model.Job;
import ar.org.cpci.bit.repository.JobRepository;

@Controller
public class JobController {

    @Autowired
    private JobRepository repository;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/job")
    public String getJobList(Model model, @PageableDefault(size = 5) Pageable page) {
        Iterable<Job> jobs = repository.findAll(page);
        model.addAttribute("jobs", jobs);
        return "job_list";
    }

    @GetMapping("/job/{id}")
    public String getJobDetail(@PathVariable Long id, Model model) {
        Optional<Job> job = repository.findById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get());
        }
        return "job_detail";
    }

    @GetMapping("/job/edit")
    public String jobEdit(Job job) {
        return "job_edit";
    }

    @PostMapping("/job/edit")
    public String jobEdit(@Valid Job job, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            repository.save(job);
            return "redirect:/job_list";
        }
        return "job_edit";
    }

}
