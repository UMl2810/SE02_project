package fit.se2.se02_project;

import fit.se2.se02_project.controller.OrderController;
import fit.se2.se02_project.model.*;
import fit.se2.se02_project.repositories.*;
import fit.se2.se02_project.request.PlaceOrderRequest;
import fit.se2.se02_project.request.ProductQuantityRequest;
import fit.se2.se02_project.service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private CommonService commonService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderStatusRepository orderStatusRepository;
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemsRepository cartItemsRepository;
    @Mock
    private ProductStatusRepository productStatusRepository;

    @Mock
    private Model model;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setRole(new Role(1L, "ADMIN"));
        when(commonService.getCurrentUser()).thenReturn(user);
    }

    @Test
    void testHistory_ReturnsHistoryView() {
        Long statusId = 1L;

        Orderstatus status = new Orderstatus();
        status.setId(statusId);
        status.setName("Pending");

        Order order = new Order();
        order.setId(100L);
        order.setUser(user);
        order.setOrderstatus(status);
        order.setFirstname("John");
        order.setLastName("Doe");
        order.setAddress("123 Street");

        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderStatusRepository.findAll()).thenReturn(List.of(status));
        when(orderStatusRepository.findById(statusId)).thenReturn(Optional.of(status));
        when(orderDetailRepository.findAll()).thenReturn(List.of());

        String view = orderController.history(statusId, model);
        assertEquals("history", view);
        verify(model).addAttribute(eq("count"), anyLong());
        verify(model).addAttribute(eq("history"), anyList());
        verify(model).addAttribute("user", user);
    }

    @Test
    void testHistory_RedirectsIfUserIsNull() {
        when(commonService.getCurrentUser()).thenReturn(null);
        String view = orderController.history(1L, model);
        assertEquals("redirect:/auth/login", view);
    }
    @Test
    void testAddOrder_Successfully() {
        // Mô phỏng dữ liệu đầu vào
        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setAddress("123 Main St");
        request.setPhone("123456789");
        request.setNote("Giao nhanh");
        request.setPayment(Integer.parseInt("1"));

        ProductQuantityRequest productReq = new ProductQuantityRequest();
        productReq.setProductID(1L);
        productReq.setQuantity(2);
        productReq.setPrice(new BigDecimal("500.00"));
        request.setProductInfor(List.of(productReq));

        // Mô phỏng dữ liệu liên quan
        Product product = new Product();
        product.setId(1L);
        product.setQuantity(10);
        product.setIsActive((byte) 1);
        product.setProductstatus(new Productstatus(1L, "Available"));

        Cart userCart = new Cart();
        userCart.setId(1L);
        userCart.setUser(user);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setName("COD");

        Orderstatus orderStatus = new Orderstatus();
        orderStatus.setId(5L);
        orderStatus.setName("Pending");

        when(commonService.getCurrentUser()).thenReturn(user);
        when(orderStatusRepository.findById(5L)).thenReturn(Optional.of(orderStatus));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.getTotal(anyLong())).thenReturn(new BigDecimal("1000.00"));
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        when(cartRepository.findAll()).thenReturn(List.of(userCart));
        when(cartItemsRepository.findAll()).thenReturn(List.of());
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productStatusRepository.findById(4L)).thenReturn(Optional.of(new Productstatus(4L, "Out of Stock")));

        // Gọi controller
        String result = orderController.add(request, model);

        assertTrue(result.contains("redirect:/cart/index?orderSuccess="));
        verify(orderRepository).save(any(Order.class));
        verify(orderDetailRepository).save(any(Orderdetail.class));
        verify(transactionRepository).save(any(Transaction.class));
        verify(productRepository, atLeastOnce()).save(any(Product.class));
    }

    @Test
    void testAddOrder_ExceptionHandling() {
        // Simulate exception
        when(commonService.getCurrentUser()).thenThrow(new RuntimeException("Unexpected"));

        PlaceOrderRequest request = new PlaceOrderRequest();
        String result = orderController.add(request, model);

        assertEquals("redirect:/error/index", result);
    }


//    @Test
//    void testCancelOrder_Successfully() {
//        Long orderId = 1L;
//
//        // Mock order status
//        Orderstatus statusPending = new Orderstatus(5L, "Pending");
//        Orderstatus statusCancelled = new Orderstatus(4L, "Cancelled");
//
//        // Mock order
//        Order order = new Order();
//        order.setId(orderId);
//        order.setOrderstatus(statusPending);
//
//        // Mock product and order detail
//        Product product = new Product();
//        product.setId(1L);
//        product.setQuantity(5);
//
//        Orderdetail orderDetail = new Orderdetail();
//        orderDetail.setOrder(order);
//        orderDetail.setProduct(product);
//        orderDetail.setQuantity(2);
//
//        // Mock repository behavior
//        when(orderRepository.findAll()).thenReturn(List.of(order));
//        when(orderDetailRepository.findAll()).thenReturn(List.of(orderDetail));
//        when(orderStatusRepository.findById(4L)).thenReturn(Optional.of(statusCancelled));
//
//        // Call the method
//        String result = orderController.cancel(orderId);
//
//        // Assertions
//        assertEquals("redirect:/order/history/5", result);
//        assertEquals(statusCancelled, order.getOrderstatus());
//        assertEquals(7, product.getQuantity()); // 5 + 2 from orderDetail
//
//        // Verify interactions
//        verify(orderRepository).save(order);
//        verify(productRepository).save(product);
//    }


    @Test
    void testCancelOrder_Exception() {
        // Lỗi xảy ra trong quá trình xử lý
        when(orderRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        String result = orderController.cancel(99L);
        assertEquals("error", result);
    }


    @Test
    void testUpdateOrderStatus_Success() {
        Long orderId = 1L;
        Long statusId = 3L;

        Order order = new Order();
        order.setId(orderId);

        Orderstatus status = new Orderstatus(statusId, "Shipping");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderStatusRepository.findById(statusId)).thenReturn(Optional.of(status));

        String result = orderController.update(orderId, statusId);

        assertEquals("redirect:/order/all/3", result);
        verify(orderRepository).save(order);
    }

}