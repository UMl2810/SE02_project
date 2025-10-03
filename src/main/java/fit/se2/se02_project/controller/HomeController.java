package fit.se2.se02_project.controller;

import fit.se2.se02_project.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private CommonService commonService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("feedbacks", commonService.getFeaturedFeedbacks());
        model.addAttribute("categories", commonService.getActiveCategories());
        model.addAttribute("user", commonService.getCurrentUser());
        return "index";
    }
}
