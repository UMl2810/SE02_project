package fit.se2.se02_project;// AboutControllerTest.java

import fit.se2.se02_project.controller.AboutController;
import fit.se2.se02_project.model.User;
import fit.se2.se02_project.service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class AboutControllerTest {

    @Mock
    private CommonService commonService;

    @InjectMocks
    private AboutController aboutController;

    @Mock
    private Model model;

    @Test
    void testAbout_ReturnsAboutViewWithUser() {
        User mockUser = new User();
        mockUser.setUsername("john_doe");

        when(commonService.getCurrentUser()).thenReturn(mockUser);

        String viewName = aboutController.about(model);

        assertEquals("about", viewName);
        verify(model).addAttribute("user", mockUser);
    }
}

