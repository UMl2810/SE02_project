package fit.se2.se02_project.controller;

import fit.se2.se02_project.model.User;
import fit.se2.se02_project.repositories.RoleRepository;
import fit.se2.se02_project.repositories.UserRepository;
import fit.se2.se02_project.request.LoginRequest;
import fit.se2.se02_project.request.RegisterRequest;
import fit.se2.se02_project.service.CommonService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CommonService commonService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        try {
            Map<String, String> errorList = new HashMap<>();
            if (request.getUsername() == null || Objects.equals(request.getUsername(), "")) {
                errorList.put("NullUsername", "* Username cannot be empty");
            }
            if (request.getPassword() == null || Objects.equals(request.getPassword(), "")) {
                errorList.put("NullPassword", "* Password cannot be empty");
            }
            if (!errorList.isEmpty()) {
                model.addAttribute("errorList", errorList);
                return "login";
            }
            String hashedPassword = commonService.hash(request.getPassword());
            String hashedPasswordLower = hashedPassword.toLowerCase();
            User user = userRepository.findAll()
                    .stream()
                    .filter(u -> Objects.equals(u.getUsername(), request.getUsername()) && Objects.equals(u.getPassword(), hashedPasswordLower))
                    .findAny()
                    .orElse(null);
            if (user == null) {
                errorList.put("Invalid", "* Username or Password is not valid");
                model.addAttribute("errorList", errorList);
                return "login";
            }

            String token = commonService.createToken(user);
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(30 * 60);
            response.addCookie(cookie);

            if (user.getRole().getId() == 1) {
                int currentMonth = LocalDate.now().getMonthValue();
                int currentYear = LocalDate.now().getYear();
                String redirectUrl = String.format("redirect:/dashboard/index?year=%d&month=%d", currentYear, currentMonth);

                return redirectUrl;
            } else {
                return "redirect:/";
            }
        } catch (Exception ex) {
            return "error";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request, Model model) {
        Map<String, String> errorList = new HashMap<>();
        if (request.getFirstName() == null || Objects.equals(request.getFirstName(), "")) {
            errorList.put("NullFirstname", "* First Name cannot be empty");
        }
        if (request.getLastName() == null || Objects.equals(request.getLastName(), "")) {
            errorList.put("NullLastname", "* Last Name cannot be empty");
        }
        if (request.getUsername() == null || Objects.equals(request.getUsername(), "")) {
            errorList.put("NullUsername", "* Username cannot be empty");
        }
        if (request.getEmail() == null || Objects.equals(request.getEmail(), "")) {
            errorList.put("NullEmail", "* Email cannot be empty");
        }
        if (request.getAddress() == null || Objects.equals(request.getAddress(), "")) {
            errorList.put("NullAddress", "* Address cannot be empty");
        }
        if (request.getPassword() == null || Objects.equals(request.getPassword(), "")) {
            errorList.put("NullPassword", "* Password cannot be empty");
        }
        if (request.getConfirmPassword() == null || Objects.equals(request.getConfirmPassword(), "")) {
            errorList.put("NullConfirmPassword", "* Confirm password cannot be empty");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            errorList.put("NotMatch", "* Password does not match");
        }
        if (userRepository.findAll()
                .stream()
                .anyMatch(u -> Objects.equals(u.getUsername(), request.getUsername()))
                ) {
            errorList.put("ExistUsername", "* Username has already existed");
        }
        if (userRepository.findAll()
                .stream()
                .anyMatch(u -> Objects.equals(u.getEmail(), request.getEmail()))) {
            errorList.put("ExistEmail", "* Email has already existed");
        }
        if (!errorList.isEmpty()) {
            model.addAttribute("errorList", errorList);
            return "register";
        } else {
            String verifyCode;
            while (true) {
                String rndstr = commonService.createRandomString(6);
                verifyCode = rndstr.toUpperCase();
                String finalVerifyCode = verifyCode;
                if (!userRepository.findAll()
                        .stream()
                        .anyMatch(u -> Objects.equals(u.getVerifyCode(), finalVerifyCode))) {
                    break;
                }
            }

            User newUser = new User();
            newUser.setFirstName(request.getFirstName());
            newUser.setLastName(request.getLastName());
            newUser.setUsername(request.getUsername());
            newUser.setEmail(request.getEmail());
            newUser.setPassword(commonService.hash(request.getPassword()));
            newUser.setAddress(request.getAddress());
            newUser.setCreateAt(new Timestamp(System.currentTimeMillis()));
            newUser.setIsActive((byte) 0);
            newUser.setRole(roleRepository.findById(2L).orElse(null));
            newUser.setVerifyCode(verifyCode);
            userRepository.save(newUser);

            String body = String.format("Your code verify is: %s", newUser.getVerifyCode());
            commonService.sendEmail("Verify Email", body, newUser.getEmail());
            return "redirect:/auth/verify";
        }
    }

    @GetMapping("/verify")
    public String verify() {
        return "verify";
    }

    @PostMapping("/verify")
    public String verify(@RequestParam("Code") String code, Model model) {
        Map<String, String> errorList = new HashMap<>();
        User unverifiedUser = userRepository.findAll()
                .stream()
                .filter(u -> Objects.equals(u.getVerifyCode(), code.toUpperCase()))
                .findAny()
                .orElse(null);
        if (unverifiedUser != null) {
            if (unverifiedUser.getIsActive() == 0) {
                unverifiedUser.setVerifyAt(new Timestamp(System.currentTimeMillis()));
                unverifiedUser.setIsActive((byte) 1);
                userRepository.save(unverifiedUser);
            } else {
                errorList.put("Verified", "* User had already verified");
            }
        } else {
            errorList.put("InvalidCode", "* Invalid code");
        }
        if (!errorList.isEmpty()) {
            model.addAttribute("errorList", errorList);
            return "verify";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("Email") String email, Model model) {
        User user = userRepository.findAll()
                .stream()
                .filter(u -> Objects.equals(u.getEmail(), email))
                .findAny()
                .orElse(null);
        if (user != null) {
            String resetPassword = commonService.createRandomString(10);
            user.setPassword(commonService.hash(resetPassword));
            userRepository.save(user);
            String body = "Your Password is: " + resetPassword;
            commonService.sendEmail("Reset Password", body, email);
            model.addAttribute("Success", "We've sent new password to your email, please check your email");
        } else {
            model.addAttribute("Error", "Your email hasn't been used");
        }
        return "resetPassword";
    }



}
