package fit.se2.se02_project;

import fit.se2.se02_project.controller.DashboardController;
import fit.se2.se02_project.dto.*;
import fit.se2.se02_project.model.*;
import fit.se2.se02_project.repositories.*;
import fit.se2.se02_project.service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DashboardControllerTest {

    @InjectMocks
    private DashboardController dashboardController;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CommonService commonService;

    @Mock
    private Model model;

    private User adminUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adminUser = new User();
        Role adminRole = new Role();
        adminRole.setId(1);
        adminUser.setRole(adminRole);
    }

    @Test
    public void testIndex_WithValidAdmin() {
        int year = 2024, month = 3;
        when(commonService.getCurrentUser()).thenReturn(adminUser);

        List<CountProductDTO> mockData = List.of(
                new CountProductDTO("Product A", 10),
                new CountProductDTO("Product B", 20)
        );
        when(productRepository.getProductsummary(year, month)).thenReturn(mockData);

        String viewName = dashboardController.index(year, month, model);

        verify(model).addAttribute(eq("labels"), anyList());
        verify(model).addAttribute(eq("values"), anyList());
        assertEquals("productsummary", viewName);
    }

    @Test
    public void testIndex_WithInvalidUser() {
        when(commonService.getCurrentUser()).thenReturn(null);
        String result = dashboardController.index(2024, 3, model);
        assertEquals("redirect:/auth/login", result);
    }

    @Test
    public void testTopCategories_WithValidAdmin() {
        int year = 2024, month = 3;
        when(commonService.getCurrentUser()).thenReturn(adminUser);

        List<CountCategoryDTO> mockData = List.of(
                new CountCategoryDTO("Category A", 15),
                new CountCategoryDTO("Category B", 25)
        );
        when(categoryRepository.getSummary(year, month)).thenReturn(mockData);

        String view = dashboardController.topCategories(year, month, model);
        verify(model).addAttribute(eq("labels"), anyList());
        verify(model).addAttribute(eq("values"), anyList());
        assertEquals("categorysummary", view);
    }

    @Test
    public void testProfit_WithValidAdmin() {
        int year = 2024;
        when(commonService.getCurrentUser()).thenReturn(adminUser);

        Order order = new Order();
        order.setId(1L);
        order.setOrderDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 15, 0, 0)));

        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        transaction.setTotal(BigDecimal.valueOf(100));

        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        String view = dashboardController.profit(year, model);
        verify(model).addAttribute(eq("labels"), anyList());
        verify(model).addAttribute(eq("values"), anyList());
        assertEquals("profit", view);
    }

    @Test
    public void testProfit_WithInvalidUser() {
        when(commonService.getCurrentUser()).thenReturn(null);
        String result = dashboardController.profit(2024, model);
        assertEquals("redirect:/auth/login", result);
    }
}
