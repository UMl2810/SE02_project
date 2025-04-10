package fit.se2.se02_project;

import fit.se2.se02_project.controller.AuthController;
import fit.se2.se02_project.model.Role;
import fit.se2.se02_project.model.User;
import fit.se2.se02_project.repositories.RoleRepository;
import fit.se2.se02_project.repositories.UserRepository;
import fit.se2.se02_project.request.LoginRequest;
import fit.se2.se02_project.request.RegisterRequest;
import fit.se2.se02_project.service.CommonService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock private CommonService commonService;
    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private Model model;
    @Mock private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginGet_ReturnsLoginView() {
        assertEquals("login", authController.login());
    }

    @Test
    void testLoginPost_WithEmptyUsernameAndPassword_ShouldReturnLoginWithErrors() {
        LoginRequest loginRequest = new LoginRequest();
        String view = authController.login(loginRequest, mock(HttpServletResponse.class), model, redirectAttributes);
        assertEquals("login", view);
        verify(model).addAttribute(eq("errorList"), any());
    }

    @Test
    void testLoginPost_WithValidCredentials_ShouldRedirectBasedOnRole() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("user1");
        request.setPassword("password");

        User user = new User();
        user.setUsername("user1");
        user.setPassword("hashedpassword");
        user.setRole(new Role(1L, "ADMIN"));

        when(commonService.hash("password")).thenReturn("hashedpassword");
        when(userRepository.findByUsernameAndPassword("user1", "hashedpassword")).thenReturn(Optional.of(user));
        when(commonService.createToken(user)).thenReturn("token123");

        HttpServletResponse response = mock(HttpServletResponse.class);

        // Act
        String result = authController.login(request, response, model, redirectAttributes);

        // Assert
        assertTrue(result.startsWith("redirect:/"));
    }

    @Test
    void testRegisterGet_ReturnsRegisterView() {
        assertEquals("register", authController.register());
    }

    @Test
    void testRegisterPost_WithEmptyFields_ShouldReturnRegisterWithErrors() {
        RegisterRequest request = new RegisterRequest();
        String result = authController.register(request, model);
        assertEquals("register", result);
        verify(model).addAttribute(eq("errorList"), any());
    }

    @Test
    void testRegisterPost_WithValidInput_ShouldRedirectToVerify() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setUsername("john123");
        request.setEmail("john@example.com");
        request.setPassword("123456");
        request.setConfirmPassword("123456");
        request.setAddress("123 Street");

        when(userRepository.findAll()).thenReturn(List.of());
        when(commonService.createRandomString(6)).thenReturn("ABC123");
        when(commonService.hash("123456")).thenReturn("hashedpass");
        when(roleRepository.findById(2L)).thenReturn(Optional.of(new Role(2L, "USER")));

        String result = authController.register(request, model);
        assertEquals("redirect:/auth/verify", result);
        verify(commonService).sendEmail(any(), any(), eq("john@example.com"));
    }

    @Test
    void testVerifyGet_ReturnsVerifyView() {
        assertEquals("verify", authController.verify());
    }

    @Test
    void testVerifyPost_WithValidCode_ShouldRedirectToLogin() {
        User user = new User();
        user.setVerifyCode("ABC123");
        user.setIsActive((byte) 0);

        when(userRepository.findAll()).thenReturn(List.of(user));
        String result = authController.verify("ABC123", model);

        assertEquals("verify", result);
    }

    @Test
    void testResetPasswordGet_ReturnsResetPasswordView() {
        assertEquals("resetPassword", authController.resetPassword());
    }

    @Test
    void testResetPasswordPost_WithValidEmail_ShouldSendNewPassword() {
        User user = new User();
        user.setEmail("user@example.com");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(commonService.createRandomString(10)).thenReturn("newpass123");
        when(commonService.hash("newpass123")).thenReturn("hashed");

        String result = authController.resetPassword("user@example.com", model);
        assertEquals("resetPassword", result);
        verify(model).addAttribute(eq("Success"), contains("sent new password"));
        verify(commonService).sendEmail(any(), contains("Your Password"), eq("user@example.com"));
    }

    @Test
    void testLogout_ShouldClearTokenCookieAndRedirect() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        String result = authController.logout(response);
        assertEquals("redirect:/", result);
        verify(response).addCookie(argThat(cookie ->
                cookie.getName().equals("token") &&
                        cookie.getMaxAge() == 0
        ));
    }
}