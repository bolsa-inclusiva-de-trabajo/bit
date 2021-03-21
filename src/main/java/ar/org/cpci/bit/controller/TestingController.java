package ar.org.cpci.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ar.org.cpci.bit.model.Job;
import ar.org.cpci.bit.model.User;
import ar.org.cpci.bit.repository.JobRepository;
import ar.org.cpci.bit.repository.UserRepository;

@Controller
public class TestingController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobsRepository;

    @GetMapping("/testing")
    public String testing(Model model) {

        User user = userRepository.findById(2L).get();
        Job job = jobsRepository.findById(4L).get();

        user.remInterestingJob(job);

        userRepository.save(user);
        jobsRepository.save(job);

        model.addAttribute("user", user);
        model.addAttribute("job", job);

        return "testing";
    }
}
