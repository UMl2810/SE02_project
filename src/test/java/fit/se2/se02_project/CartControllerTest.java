package fit.se2.se02_project;

import fit.se2.se02_project.controller.CartController;
import fit.se2.se02_project.dto.CartItemDTO;
import fit.se2.se02_project.model.*;
import fit.se2.se02_project.repositories.CartItemsRepository;
import fit.se2.se02_project.repositories.CartRepository;
import fit.se2.se02_project.repositories.PaymentRepository;
import fit.se2.se02_project.repositories.ProductRepository;
import fit.se2.se02_project.request.AddtoCartRequest;
import fit.se2.se02_project.service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemsRepository cartItemsRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CommonService commonService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIndex_WithLoggedInUser_ShouldReturnCartView() {
        User user = new User();
        user.setId(1L);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);

        CartItemDTO cartItemDTO = new CartItemDTO(1L, 1L, "Product", 2, BigDecimal.valueOf(200), "image.jpg");

        when(commonService.getCurrentUser()).thenReturn(user);
        when(cartRepository.findAll()).thenReturn(List.of(cart));
        when(cartItemsRepository.findAll()).thenReturn(List.of());
        when(cartRepository.getTotal(cart.getId())).thenReturn(BigDecimal.valueOf(200));
        when(paymentRepository.findAll()).thenReturn(List.of());

        String result = cartController.index(model, null, null);

        assertEquals("cart", result);
        verify(model).addAttribute(eq("user"), eq(user));
        verify(model).addAttribute(eq("products"), anyList());
        verify(model).addAttribute(eq("total"), eq(BigDecimal.valueOf(200)));
        verify(model).addAttribute(eq("payments"), anyList());
    }

    @Test
    void testAdd_WithValidRequest_ShouldAddProductToCart() {
        User user = new User();
        user.setId(1L);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);

        Product product = new Product();
        product.setId(1L);

        AddtoCartRequest request = new AddtoCartRequest();
        request.setProductId(1L);
        request.setQuantity(2);

        when(commonService.getCurrentUser()).thenReturn(user);
        when(cartRepository.findAll()).thenReturn(List.of(cart));
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(cartItemsRepository.findAll()).thenReturn(List.of());

        String result = cartController.add(request, model);

        assertEquals("redirect:/cart/index", result);
        verify(cartItemsRepository).save(any(Cartitem.class));
    }

    @Test
    void testDelete_WithValidProductId_ShouldRemoveProductFromCart() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);

        Product product = new Product();
        product.setId(1L);

        Cartitem cartItem = new Cartitem();
        cartItem.setId(1L);
        cartItem.setCart(cart);
        cartItem.setProduct(product);

        when(commonService.getCurrentUser()).thenReturn(user);
        when(cartRepository.findAll()).thenReturn(List.of(cart));
        when(cartItemsRepository.findAll()).thenReturn(List.of(cartItem));

        // Act
        String result = cartController.delete(1L);

        // Assert
        assertEquals("redirect:/cart/index", result);
        verify(cartItemsRepository).delete(cartItem);
    }

    @Test
    void testUp_WithValidCartItemId_ShouldIncreaseQuantity() {
        Cartitem cartItem = new Cartitem();
        cartItem.setId(1L);
        cartItem.setQuantity(1);

        when(cartItemsRepository.findById(1L)).thenReturn(Optional.of(cartItem));

        String result = cartController.up(1L);

        assertEquals("redirect:/cart/index", result);
        verify(cartItemsRepository).save(cartItem);
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    void testDown_WithValidCartItemId_ShouldDecreaseQuantity() {
        Cartitem cartItem = new Cartitem();
        cartItem.setId(1L);
        cartItem.setQuantity(2);

        when(cartItemsRepository.findById(1L)).thenReturn(Optional.of(cartItem));

        String result = cartController.down(1L);

        assertEquals("redirect:/cart/index", result);
        verify(cartItemsRepository).save(cartItem);
        assertEquals(1, cartItem.getQuantity());
    }
}