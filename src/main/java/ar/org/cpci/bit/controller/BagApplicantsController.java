package ar.org.cpci.bit.controller;

        import java.util.Optional;

        import javax.validation.Valid;

        import ar.org.cpci.bit.model.User;
        import ar.org.cpci.bit.repository.UserRepository;
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
public class BagApplicantsController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/bagapplicants")
    public String getBagApplicants(Model model, @PageableDefault(size = 5) Pageable page) {
        Iterable<User> users = repository.findAll(page);
        model.addAttribute("users", users);
        return "bag_applicants";
    }



}
