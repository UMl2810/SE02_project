package fit.se2.se02_project.controller;

import fit.se2.se02_project.model.User;
import fit.se2.se02_project.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutController {
    @Autowired
    private CommonService commonService;
    @GetMapping("/index")
    public String about(Model model) {
        User currentUser = commonService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "about";
    }
}
