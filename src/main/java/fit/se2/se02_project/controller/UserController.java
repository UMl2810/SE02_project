package fit.se2.se02_project.controller;

import fit.se2.se02_project.model.User;
import fit.se2.se02_project.repositories.UserRepository;
import fit.se2.se02_project.request.ChangeRequest;
import fit.se2.se02_project.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/profile")
    public String profile(Model model, @RequestParam(required = false) Map<String, String> responseMessage) {
        model.addAttribute("response", responseMessage);
        User currentUser = commonService.getCurrentUser();
        if (currentUser == null){
            return "redirect:/auth/login";
        }
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/change")
    public String change(ChangeRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "Change password successfully");
        try {
            Map<String, String> errorList = new HashMap<>();
            if (request.getCurrentPass() == null || request.getNewPass() == null || request.getConfirmNewPass() == null) {
                errorList.put("Empty", "* Cannot be empty!");
            }
            if (!request.getNewPass().equals(request.getConfirmNewPass())) {
                errorList.put("NotMatch", "* Passwords do not match");
            }
            if (!errorList.isEmpty()) {
                return "redirect:/user/profile?" + buildQueryString(errorList);
            }
            User user = commonService.getCurrentUser();
            if (!user.getPassword().equals(commonService.hash(request.getCurrentPass()))) {
                errorList.put("InvalidPass", "* Your current password is not correct");
                return "redirect:/user/profile?" + buildQueryString(errorList);
            }

            User changeUser = userRepository.findById(user.getId()).orElse(null);
            if (changeUser != null) {
                changeUser.setPassword(commonService.hash(request.getNewPass()));
                userRepository.save(changeUser);
            }
            return "redirect:/user/profile?success=Change password successfully";
        } catch (Exception ex) {
            return "redirect:/error?message=" + ex.getMessage();
        }
    }

    private String buildQueryString(Map<String, String> params) {
        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            queryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return queryString.toString();
    }
}
