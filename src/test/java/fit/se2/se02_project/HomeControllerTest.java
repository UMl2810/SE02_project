package fit.se2.se02_project;

import fit.se2.se02_project.controller.HomeController;
import fit.se2.se02_project.dto.CategoryDTO;
import fit.se2.se02_project.dto.FeedbackDTO;
import fit.se2.se02_project.model.*;
import fit.se2.se02_project.repositories.CategoryRepository;
import fit.se2.se02_project.repositories.FeedbackRepository;
import fit.se2.se02_project.service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CommonService commonService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIndex_ReturnsIndexViewAndAddsAttributes() {
        // Mock current user
        User user = new User();
        when(commonService.getCurrentUser()).thenReturn(user);

        // Mock feedbacks (only rate == 3 should be included)
        Feedback f1 = new Feedback();
        f1.setName("Alice");
        f1.setComment("Nice!");
        f1.setRate((short) 3);

        Feedback f2 = new Feedback();
        f2.setName("Bob");
        f2.setComment("Good");
        f2.setRate((short) 4); // should be excluded

        when(feedbackRepository.findAll()).thenReturn(List.of(f1, f2));

        // Mock categories (only isActive == 1 should be included)
        Category c1 = new Category();
        c1.setId(1L);
        c1.setCategoryName("SUV");
        c1.setDescription("Spacious car");
        Product p = new Product();
        p.setImage("image.jpg");
        c1.setProducts(Set.of(p));
        c1.setIsActive((byte) 1);

        Category c2 = new Category();
        c2.setIsActive((byte) 0); // should be excluded

        when(categoryRepository.findAll()).thenReturn(List.of(c1, c2));

        // Execute
        String view = homeController.index(model);

        // Verify
        verify(model).addAttribute(eq("feedbacks"), anyList());
        verify(model).addAttribute(eq("categories"), anyList());
        verify(model).addAttribute("user", user);

        assertEquals("index", view);
    }
}