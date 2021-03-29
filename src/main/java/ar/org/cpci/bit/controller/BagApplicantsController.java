package ar.org.cpci.bit.controller;

        import java.util.List;
        import java.util.Locale;
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
        import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BagApplicantsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/bagapplicants")
    public String getBagApplicants(Model model, @PageableDefault(size = 4) Pageable page,
                                   @RequestParam("page") Optional<Integer> p,
                                   @RequestParam("size") Optional<Integer> s) {

        //Iterable<User> users = userRepository.findAll(page);
        Iterable<User> users = userRepository.findByApplyForJobTrueAndDisabledFalse(page);

        model.addAttribute("users", users);

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

        return "bag_applicants";
    }

    @GetMapping("/bagapplicants/text/{filterText}")
    public String getBagApplicantsTextFiltered(Model model, @PageableDefault(size = 100) Pageable page,
                                           @RequestParam("page") Optional<Integer> p,
                                           @RequestParam("size") Optional<Integer> s,
                                           @PathVariable String filterText) {


        Iterable<User> users = userRepository.findTextFilteredUsers(page, filterText.toLowerCase(Locale.ROOT));
        model.addAttribute("users", users);

        User user = findUser();
        model.addAttribute("user", user);

        int currentPage = p.orElse(0);
        int pageSize = s.orElse(100);
        int pageTotal = findPageTotal(pageSize);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("filterType", "text");
        model.addAttribute("filterContent", filterText);

        return "bag_applicants";
    }

    @GetMapping("/bagapplicants/city/{filterCity}")
    public String getBagOffersCityFiltered(Model model, @PageableDefault(size = 100) Pageable page,
                                           @RequestParam("page") Optional<Integer> p,
                                           @RequestParam("size") Optional<Integer> s,
                                           @PathVariable Boolean filterCity) {

        User user = findUser();
        model.addAttribute("user", user);

        long fCity = 0;
        String sCity = user.getCity().getName();
        if (filterCity) {
            fCity = user.getCity().getId();
        }

        Iterable<User> users = userRepository.findCityFilteredUsers(page, fCity);
        model.addAttribute("users", users);

        int currentPage = p.orElse(0);
        int pageSize = s.orElse(100);
        int pageTotal = findPageTotal(pageSize);


        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("filterType", "city");
        model.addAttribute("filterContent", sCity);
        return "bag_applicants";
    }

    @GetMapping("/bagapplicants/fullparthome/{filterType}")
    public String getApplicantsFullPartHomeFiltered(Model model, @PageableDefault(size = 100) Pageable page,
                                             @RequestParam("page") Optional<Integer> p,
                                             @RequestParam("size") Optional<Integer> s,
                                             @PathVariable String filterType) {
        User user = findUser();
        model.addAttribute("user", user);
        Iterable<User> users = null;
        String filterContent = "";

        if (filterType.equals("full")) {
            users = userRepository.findByApplyForJobTrueAndDisabledFalseAndFullTimeTrue(page);
            filterContent = "PERSONAS CON TIEMPO COMPLETO";
        } else {
            if (filterType.equals("part")) {
                users = userRepository.findByApplyForJobTrueAndDisabledFalseAndPartTimeTrue(page);
                filterContent = "PERSONAS CON MEDIO TIEMPO";
            } else {
                if (filterType.equals("home")) {
                    users = userRepository.findByApplyForJobTrueAndDisabledFalseAndHomeWorkTrue(page);
                    filterContent = "PERSONAS CON TRABAJO REMOTO";
                } else {
                    users = userRepository.findByApplyForJobTrueAndDisabledFalse(page);
                    filterContent = "";
                }
            }
        }

        model.addAttribute("users", users);

        int currentPage = p.orElse(0);
        int pageSize = s.orElse(100);
        int pageTotal = findPageTotal(pageSize);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("filterType", filterType);
        model.addAttribute("filterContent", filterContent);
        return "bag_applicants";



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
        List<User> userstotal = userRepository.findAll();
        int pageTotal = 1;

        if (userstotal.size() > pageSize && pageSize > 0) {
            double factor = (double)userstotal.size() / (double)pageSize;
            if (factor > 1) {
                pageTotal = ((int)factor) + 1;
            }
        }
        return pageTotal;
    }

}
