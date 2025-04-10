package fit.se2.se02_project;

import fit.se2.se02_project.controller.UserController;
import fit.se2.se02_project.model.Role;
import fit.se2.se02_project.model.User;
import fit.se2.se02_project.repositories.UserRepository;
import fit.se2.se02_project.request.ChangeRequest;
import fit.se2.se02_project.service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private CommonService commonService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    private User mockUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setPassword("hashed_oldpass");
        mockUser.setRole(new Role(2L, "USER"));
    }

    @Test
    public void testProfile_ReturnsRedirectIfNotLoggedIn() {
        when(commonService.getCurrentUser()).thenReturn(null);
        String result = userController.profile(model, new HashMap<>());
        assertEquals("redirect:/auth/login", result);
    }

    @Test
    public void testProfile_ReturnsProfileIfLoggedIn() {
        when(commonService.getCurrentUser()).thenReturn(mockUser);
        String result = userController.profile(model, new HashMap<>());
        verify(model).addAttribute("user", mockUser);
        assertEquals("profile", result);
    }

    @Test
    public void testChangePassword_Success() {
        ChangeRequest request = new ChangeRequest();
        request.setCurrentPass("oldpass");
        request.setNewPass("newpass123");
        request.setConfirmNewPass("newpass123");

        when(commonService.getCurrentUser()).thenReturn(mockUser);
        when(commonService.hash("oldpass")).thenReturn("hashed_oldpass");
        when(commonService.hash("newpass123")).thenReturn("hashed_newpass123");
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        String result = userController.change(request, redirectAttributes);

        verify(userRepository, times(1)).save(any(User.class));
        assertTrue(result.contains("redirect:/user/profile?success="));
    }

    @Test
    public void testChangePassword_PasswordsNotMatch() {
        ChangeRequest request = new ChangeRequest();
        request.setCurrentPass("oldpass");
        request.setNewPass("newpass123");
        request.setConfirmNewPass("wrongpass");

        String result = userController.change(request, redirectAttributes);
        assertTrue(result.contains("NotMatch"));
    }

    @Test
    public void testChangePassword_InvalidCurrentPassword() {
        ChangeRequest request = new ChangeRequest();
        request.setCurrentPass("wrongpass");
        request.setNewPass("newpass123");
        request.setConfirmNewPass("newpass123");

        when(commonService.getCurrentUser()).thenReturn(mockUser);
        when(commonService.hash("wrongpass")).thenReturn("wrong_hashed");

        String result = userController.change(request, redirectAttributes);
        assertTrue(result.contains("InvalidPass"));
    }

    @Test
    public void testChangePassword_EmptyFields() {
        ChangeRequest request = new ChangeRequest();
        request.setCurrentPass(null);
        request.setNewPass(null);
        request.setConfirmNewPass(null);

        String result = userController.change(request, redirectAttributes);
        // Kiểm tra xem kết quả có chứa lỗi "Empty" hay không
        assertTrue(result.contains("Empty"));
    }
}