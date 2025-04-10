package fit.se2.se02_project;

import fit.se2.se02_project.controller.ProductController;
import fit.se2.se02_project.model.*;
import fit.se2.se02_project.repositories.CategoryRepository;
import fit.se2.se02_project.repositories.ProductRepository;
import fit.se2.se02_project.repositories.ProductStatusRepository;
import fit.se2.se02_project.request.AddproductRequest;
import fit.se2.se02_project.service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductStatusRepository productStatusRepository;

    @Mock
    private CommonService commonService;

    @Mock
    private Model model;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct_Success() {
        User user = new User();
        user.setUsername("admin");
        user.setRole(new Role(1L, "ADMIN"));

        AddproductRequest request = new AddproductRequest();
        request.setProductName("Laptop");
        request.setQuantity("5");
        request.setPrice("1000");
        request.setCategoryId(1L);
        request.setProductStatusId(1L);
        request.setSaleprice("900");
        request.setDescription("Gaming laptop");
        request.setImage(new MockMultipartFile("image", "image.png", "image/png", "image-content".getBytes()));

        when(commonService.getCurrentUser()).thenReturn(user);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category(1L, "Tech")));
        when(productStatusRepository.findById(1L)).thenReturn(Optional.of(new Productstatus(1L, "Available")));

        String viewName = productController.add(model, request);

        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals(true, viewName.contains("redirect:/product/add?"));
    }

    @Test
    public void testUpdateProduct_Success() {
        User user = new User();
        user.setUsername("admin");
        user.setRole(new Role(1L, "ADMIN"));

        AddproductRequest request = new AddproductRequest();
        request.setProductName("Updated Laptop");
        request.setQuantity("3");
        request.setPrice("800");
        request.setCategoryId(1L);
        request.setProductStatusId(1L);
        request.setDescription("Updated description");

        Product existingProduct = new Product();
        existingProduct.setId(10L);

        when(commonService.getCurrentUser()).thenReturn(user);
        when(productRepository.findById(10L)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category()));
        when(productStatusRepository.findById(1L)).thenReturn(Optional.of(new Productstatus()));

        String viewName = productController.update(model, 10L, request);

        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals(true, viewName.contains("redirect:/product/update/10?"));
    }

    @Test
    public void testDeleteProduct_Success() {
        Product product = new Product();
        product.setId(1L);
        product.setIsActive((byte) 1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        String viewName = productController.delete(1L);

        verify(productRepository, times(1)).save(product);
        assertEquals("redirect:/product/admin", viewName);
    }
}