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
import ar.org.cpci.bit.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/user")
    public String getJobList(Model model, @PageableDefault(size = 5) Pageable page) {
        Iterable<User> users = repository.findAll(page);
        model.addAttribute("jobs", users);
        return "user_list";
    }

    @GetMapping("/user/{id}")
    public String getJobDetail(@PathVariable Long id, Model model) {
        Optional<User> user = repository.findById(id);
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
    public String jobEdit(@Valid User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            repository.save(user);
            return "redirect:/user_list";
        }
        return "user_edit";
    }

}
