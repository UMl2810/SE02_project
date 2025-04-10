package fit.se2.se02_project;

import fit.se2.se02_project.controller.FeedbackController;
import fit.se2.se02_project.dto.FeedbackDTO;
import fit.se2.se02_project.model.Feedback;
import fit.se2.se02_project.model.Role;
import fit.se2.se02_project.model.User;
import fit.se2.se02_project.repositories.FeedbackRepository;
import fit.se2.se02_project.request.FeedbackRequest;
import fit.se2.se02_project.service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FeedbackControllerTest {

    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private CommonService commonService;

    @Mock
    private Model model;

    private User adminUser;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        adminUser = new User();
        Role role = new Role();
        role.setId(1);
        adminUser.setRole(role);
    }

    @Test
    void testIndex() {
        Map<String, String> messages = new HashMap<>();
        messages.put("Success", "Test");

        when(commonService.getCurrentUser()).thenReturn(adminUser);

        String view = feedbackController.index(model, messages);

        verify(model).addAttribute("user", adminUser);
        verify(model).addAttribute("message", messages);
        assertEquals("feedback", view);
    }

    @Test
    void testAll_WithAdmin() {
        when(commonService.getCurrentUser()).thenReturn(adminUser);

        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setName("John");
        feedback.setComment("Great site!");
        feedback.setEmail("john@example.com");
        feedback.setRate((short) 5);

        when(feedbackRepository.findAll()).thenReturn(List.of(feedback));

        String view = feedbackController.all(model);

        verify(model).addAttribute(eq("feedbacks"), anyList());
        assertEquals("allfeedback", view);
    }

    @Test
    void testAll_WithNullUser_ShouldRedirect() {
        when(commonService.getCurrentUser()).thenReturn(null);

        String view = feedbackController.all(model);
        assertEquals("redirect:/auth/login", view);
    }

    @Test
    void testSend_WithInvalidRequest_ShouldRedirectWithErrors() {
        FeedbackRequest request = new FeedbackRequest(); // all fields null
        String view = feedbackController.send(request);
        assert view.contains("redirect:/feedback/index?");
        assert view.contains("NullName=");
        assert view.contains("NullEmail=");
        assert view.contains("NullRate=");
        assert view.contains("NullComment=");
    }

    @Test
    void testSend_WithValidRequest_ShouldSaveFeedback() {
        FeedbackRequest request = new FeedbackRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setRate("5");
        request.setComment("Awesome!");

        String view = feedbackController.send(request);

        verify(feedbackRepository).save(any(Feedback.class));
        assert view.contains("redirect:/feedback/index?");
        assert view.contains("Success=");
    }
}
