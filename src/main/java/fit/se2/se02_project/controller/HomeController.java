package fit.se2.se02_project.controller;

import fit.se2.se02_project.dto.CategoryDTO;
import fit.se2.se02_project.dto.FeedbackDTO;
import fit.se2.se02_project.model.Category;
import fit.se2.se02_project.model.Feedback;
import fit.se2.se02_project.model.User;
import fit.se2.se02_project.repositories.CategoryRepository;
import fit.se2.se02_project.repositories.FeedbackRepository;
import fit.se2.se02_project.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommonService commonService;

    @GetMapping("/")
    public String index(Model model) {
        User currentUser = commonService.getCurrentUser();
        List<FeedbackDTO> feedbacks = feedbackRepository.findAll()
                .stream()
                .filter(f -> f.getRate() == 3)
                .limit(3)
                .map(this::toFeedbackDTO)
                .collect(Collectors.toList());
        List<CategoryDTO> categories = categoryRepository.findAll()
                .stream()
                .filter(c -> c.getIsActive() == 1)
                .map(this::toCategoriesDTO)
                .collect(Collectors.toList());


        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("categories", categories);
        model.addAttribute("user", currentUser);

        return "index";
    }

    private CategoryDTO toCategoriesDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getCategoryName(), category.getDescription(),
                category.getProducts()
                        .stream()
                        .map(p -> p.getImage())
                        .findFirst()
                        .orElse(null));
    }

    private FeedbackDTO toFeedbackDTO(Feedback feedback) {
        return new FeedbackDTO(feedback.getName(), feedback.getComment());
    }
}
