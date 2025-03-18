package fit.se2.se02_project.service;

import fit.se2.se02_project.model.*;
import fit.se2.se02_project.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemsRepository cartItemRepository;
    @Autowired
    private ProductStatusRepository productStatusRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @PostConstruct
    @Transactional
    public void initDatabase() {
        try {
            if (roleRepository.count() == 0) {
                Role adminRole = new Role();
                adminRole.setName("Admin");
                roleRepository.save(adminRole);

                Role userRole = new Role();
                userRole.setName("User");
                roleRepository.save(userRole);
            }

            // Insert users if not present
            if (userRepository.count() == 0) {
                Role adminRole = roleRepository.findAll().stream().filter(role -> role.getName().equals("Admin"))
                        .findFirst().orElse(null);
                Role userRole = roleRepository.findAll().stream().filter(role -> role.getName().equals("User"))
                        .findFirst().orElse(null);

                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("123123"); // Hash mật khẩu nếu cần
                admin.setEmail("chicandlighting@gmail.com");
                admin.setRole(adminRole);
                userRepository.save(admin);

                User user1 = new User();
                user1.setUsername("hoangson123");
                user1.setPassword("hashed_password"); // Hash mật khẩu nếu cần
                user1.setEmail("sonnguyen10112003@gmail.com");
                user1.setRole(userRole);
                userRepository.save(user1);

                User user2 = new User();
                user2.setUsername("hoangson1234");
                user2.setPassword("hashed_password"); // Hash mật khẩu nếu cần
                user2.setEmail("sonhoang10112003@gmail.com");
                user2.setRole(userRole);
                userRepository.save(user2);
            }
            if (categoryRepository.count() == 0) {
                Category category1 = new Category();
                category1.setCategoryName("ceiling");
                category1.setDescription(
                        "Discover chic ceiling lights to elevate your space. Explore modern designs and timeless classics for a touch of elegance in any room.");
                category1.setIsActive((byte) 1);
                categoryRepository.save(category1);
                categoryRepository.flush();

                Category category2 = new Category();
                category2.setCategoryName("wall");
                category2.setDescription("Add warmth and style to your walls...");
                category2.setIsActive((byte) 1);
                categoryRepository.save(category2);
                categoryRepository.flush();

                Category category3 = new Category();
                category3.setCategoryName("lamp");
                category3.setDescription("Illuminate your space with our exquisite lamps...");
                category3.setIsActive((byte) 1);
                categoryRepository.save(category3);
                categoryRepository.flush();

                Category category4 = new Category();
                category4.setCategoryName("outdoor");
                category4.setDescription("Light up your outdoor spaces with style...");
                category4.setIsActive((byte) 1);
                categoryRepository.save(category4);
                categoryRepository.flush();

                Category category5 = new Category();
                category5.setCategoryName("fan");
                category5.setDescription("Stay cool and stylish with our range of ceiling fans...");
                category5.setIsActive((byte) 1);
                categoryRepository.save(category5);
                categoryRepository.flush();

                Category category6 = new Category();
                category6.setCategoryName("accents");
                category6.setDescription("Elevate your space with our curated selection of home accents...");
                category6.setIsActive((byte) 1);
                categoryRepository.save(category6);
                categoryRepository.flush();
            }

            // Insert product statuses if not present
            if (productStatusRepository.count() == 0) {
                Productstatus availableStatus = new Productstatus();
                availableStatus.setName("available");
                productStatusRepository.save(availableStatus);

                Productstatus saleStatus = new Productstatus();
                saleStatus.setName("sale");
                productStatusRepository.save(saleStatus);

                Productstatus bestSellerStatus = new Productstatus();
                bestSellerStatus.setName("bestseller");
                productStatusRepository.save(bestSellerStatus);

                Productstatus outOfStockStatus = new Productstatus();
                outOfStockStatus.setName("outofstock");
                productStatusRepository.save(outOfStockStatus);
            }

            // Insert products if not present
            if (productRepository.count() < 32) {
                Map<String, Category> categories = categoryRepository.findAll().stream()
                        .collect(Collectors.toMap(Category::getCategoryName, category -> category));

                Productstatus saleStatus = getSale();

                String[][] products = {
                        { "BEAU POLISHED BRASS TABLE LAMP", "399.00", "lamp", "299.00", "/img/productImg/accent.jpg" },
                        { "BIANCA MARBLE TABLE LAMP", "459.00", "lamp", "349.00", "/img/productImg/accent.jpg" },
                        { "EXPOSIOR POLISHED BRASS AND WALNUT TABLE LAMP", "499.00", "lamp", "379.00",
                                "/img/productImg/accent.jpg" },
                        { "GRAZIANO INDOOR/OUTDOOR TRAVERTINE WALL SCONCE", "289.00", "wall", "219.00",
                                "/img/productImg/accent.jpg" },
                        { "RONDA INDOOR/OUTDOOR BLACK FLUSH MOUNT LIGHT", "199.00", "wall", "149.00",
                                "/img/productImg/accent.jpg" },
                        { "RONDA INDOOR/OUTDOOR MATTE BLACK WALL SCONCE", "249.00", "wall", "189.00",
                                "/img/productImg/accent.jpg" },
                        { "RONDA INDOOR/OUTDOOR POLISHED BRASS FLUSH MOUNT LIGHT", "279.00", "wall", "209.00",
                                "/img/productImg/accent.jpg" },
                        { "5 BROWN WINGS FAN", "349.00", "fan", "299.00", "/img/productImg/accent.jpg" },
                        { "3 BROWN WINGS FAN", "299.00", "fan", "249.00", "/img/productImg/accent.jpg" },
                        { "BALL CANDLES SET OF 2", "39.00", "accents", "29.00", "/img/productImg/accent.jpg" },
                        { "BRIGHTON TAPERED POLISHED STAINLESS STEEL PENDANT LIGHT 2", "529.00", "ceiling", "419.00",
                                "/img/productImg/accent.jpg" },
                        { "ALDUS IVORY AND POLISHED BRASS ARTICULATING WALL SCONCE", "359.00", "wall", "279.00",
                                "/img/productImg/accent.jpg" },
                        { "EXPOSIOR INDOOR/OUTDOOR BLACK PENDANT LIGHT MODEL 017", "489.00", "ceiling", "379.00",
                                "/img/productImg/accent.jpg" },
                        { "5 BLACK WINGS FAN", "369.00", "fan", "319.00", "/img/productImg/accent.jpg" },
                        { "3 BROWN WINGS FAN PREMIUM", "319.00", "fan", "269.00", "/img/productImg/accent.jpg" },
                        { "5 WHITE WINGS FAN", "379.00", "fan", "329.00", "/img/productImg/accent.jpg" },
                        { "BLACK TWISTED TAPER CANDLES SET OF 2", "49.00", "accents", "39.00",
                                "/img/productImg/accent.jpg" },
                        { "BRIX GOLDEN BLACK MARBLE INCENSE BURNER", "99.00", "accents", "79.00",
                                "/img/productImg/accent.jpg" },
                        { "CINQ LARGE MULTI WHITE TAPER CANDLE HOLDER", "89.00", "accents", "69.00",
                                "/img/productImg/accent.jpg" },
                        { "DREAMER IN LONDON CEDARWOOD AND VANILLA CANDLE", "59.00", "accents", "49.00",
                                "/img/productImg/accent.jpg" },
                        { "FALCON METAL INCENSE BURNER", "79.00", "accents", "59.00", "/img/productImg/accent.jpg" },
                        { "ALDUS BLACK AND POLISHED BRASS ARTICULATING WALL SCONCE", "369.00", "wall", "289.00",
                                "/img/productImg/accent.jpg" },
                        { "5 BROWN LEAF WINGS FAN", "389.00", "fan", "339.00", "/img/productImg/accent.jpg" },
                        { "WHITE TWISTED TAPER CANDLES SET OF 2", "49.00", "accents", "39.00",
                                "/img/productImg/accent.jpg" },
                        { "CINQ MEDIUM MULTI WHITE TAPER CANDLE HOLDER", "79.00", "accents", "59.00",
                                "/img/productImg/accent.jpg" },
                        { "DREAMER IN LONDON SCENTED CANDLE", "69.00", "accents", "49.00",
                                "/img/productImg/accent.jpg" },
                        { "MYSTIC CITY LONDON SCENTED CANDLE", "79.00", "accents", "59.00",
                                "/img/productImg/accent.jpg" },
                        { "BRIGHTON TAPERED BRASS PENDANT LIGHT", "549.00", "ceiling", "439.00",
                                "/img/productImg/accent.jpg" },
                        { "CINQ SMALL MULTI WHITE TAPER CANDLE HOLDER", "69.00", "accents", "49.00",
                                "/img/productImg/accent.jpg" },
                        { "DREAMER IN LONDON SCENTED CANDLE", "69.00", "accents", "49.00",
                                "/img/productImg/accent.jpg" },
                        { "NEW CEILING LIGHT", "599.00", "ceiling", "479.00", "/img/productImg/accent.jpg" },
                        { "ASSEMBLED CERAMIC TABLE LAMP GRAY", "329.00", "lamp", "249.00",
                                "/img/productImg/accent.jpg" }
                };

                List<Product> newProducts = new ArrayList<>();
                for (String[] p : products) {
                    Product product = new Product();
                    product.setProductName(p[0]);
                    product.setPrice(new BigDecimal(p[1]));
                    product.setCategory(categories.get(p[2]));
                    product.setQuantity(10);
                    product.setIsActive((byte) 1);
                    product.setImage(p[4]);
                    product.setProductstatus(saleStatus);
                    product.setSaleprice(new BigDecimal(p[3]));
                    newProducts.add(product);
                }

                productRepository.saveAll(newProducts);
                productRepository.flush();
            }
            if (orderStatusRepository.count() == 0) {
                Orderstatus waitForDelivery = new Orderstatus();
                waitForDelivery.setName("Wait for delivery");
                waitForDelivery.setBootstapicon("bi bi-hourglass");
                orderStatusRepository.save(waitForDelivery);

                Orderstatus shipping = new Orderstatus();
                shipping.setName("Shipping");
                shipping.setBootstapicon("bi bi-truck");
                orderStatusRepository.save(shipping);

                Orderstatus accomplished = new Orderstatus();
                accomplished.setName("Accomplished");
                accomplished.setBootstapicon("bi bi-calendar2-check");
                orderStatusRepository.save(accomplished);

                Orderstatus canceled = new Orderstatus();
                canceled.setName("Canceled");
                canceled.setBootstapicon("bi bi-box-seam");
                orderStatusRepository.save(canceled);

                Orderstatus waitForConfirmation = new Orderstatus();
                waitForConfirmation.setName("Wait for confirmation");
                waitForConfirmation.setBootstapicon("bi bi-clock");
                orderStatusRepository.save(waitForConfirmation);
            }
            if (orderRepository.count() == 0) {
                User user1 = userRepository.findAll().stream()
                        .filter(user -> user.getUsername().equals("hoangson123"))
                        .findFirst().orElse(null);
                User user2 = userRepository.findAll().stream()
                        .filter(user -> user.getUsername().equals("hoangson1234"))
                        .findFirst().orElse(null);

                Map<String, Orderstatus> statusMap = orderStatusRepository.findAll().stream()
                        .collect(Collectors.toMap(Orderstatus::getName, status -> status));

                List<Order> orders = Arrays.asList(
                        createOrder(user1, statusMap.get("Wait for confirmation"), "2024-04-05 10:01:52"),
                        createOrder(user1, statusMap.get("Canceled"), "2024-04-05 15:15:01"),
                        createOrder(user1, statusMap.get("Canceled"), "2024-04-06 09:39:20"),
                        createOrder(user1, statusMap.get("Accomplished"), "2024-04-08 03:15:23"),
                        createOrder(user1, statusMap.get("Wait for confirmation"), "2024-04-08 03:17:06"),
                        createOrder(user1, statusMap.get("Wait for delivery"), "2024-04-08 03:18:23"),
                        createOrder(user1, statusMap.get("Wait for confirmation"), "2024-04-12 06:04:34",
                                "Giao gio hanh chinh"),
                        createOrder(user1, statusMap.get("Wait for delivery"), "2024-04-12 06:07:56",
                                "Giao gio hanh chinh"),
                        createOrder(user1, statusMap.get("Wait for confirmation"), "2024-04-13 09:37:24",
                                "Giao gio hanh chinh"),
                        createOrder(user2, statusMap.get("Canceled"), "2024-05-13 02:43:56", "Nguyen Duc", "Son",
                                "Yen Phong"),
                        createOrder(user2, statusMap.get("Wait for confirmation"), "2024-05-13 02:47:35", "Nguyen Duc",
                                "Son", "Yen Phong"),
                        createOrder(user1, statusMap.get("Canceled"), "2024-05-14 02:29:38", "Nguyen Duc", "Son",
                                "Yen Phong"));

                orderRepository.saveAll(orders);
            }
            // Insert payments if not present
            if (paymentRepository.count() == 0) {
                Payment cod = new Payment();
                cod.setName("COD");
                paymentRepository.save(cod);
                paymentRepository.flush();

                Payment payAtShop = new Payment();
                payAtShop.setName("Pay at shop");
                paymentRepository.save(payAtShop);
                paymentRepository.flush();
            }

            if (transactionRepository.count() == 0) {
                Order order1 = orderRepository.findById(1L).orElse(null);
                Order order2 = orderRepository.findById(2L).orElse(null);
                Order order3 = orderRepository.findById(3L).orElse(null);
                Order order4 = orderRepository.findById(4L).orElse(null);
                Order order5 = orderRepository.findById(5L).orElse(null);
                Order order6 = orderRepository.findById(6L).orElse(null);
                Order order7 = orderRepository.findById(7L).orElse(null);
                Order order8 = orderRepository.findById(8L).orElse(null);
                Order order9 = orderRepository.findById(9L).orElse(null);
                Order order10 = orderRepository.findById(10L).orElse(null);
                Order order11 = orderRepository.findById(11L).orElse(null);
                Order order12 = orderRepository.findById(12L).orElse(null);

                Payment cod = paymentRepository.findAll().stream().filter(payment -> payment.getName().equals("COD"))
                        .findFirst().orElse(null);
                Payment payAtShop = paymentRepository.findAll().stream()
                        .filter(payment -> payment.getName().equals("Pay at shop")).findFirst().orElse(null);

                Transaction transaction1 = new Transaction();
                transaction1.setOrder(order1);
                transaction1.setPayment(payAtShop);
                transaction1.setTotal(new BigDecimal("2995.00"));
                transactionRepository.save(transaction1);
                transactionRepository.flush();

                Transaction transaction2 = new Transaction();
                transaction2.setOrder(order2);
                transaction2.setPayment(payAtShop);
                transaction2.setTotal(new BigDecimal("3114.85"));
                transactionRepository.save(transaction2);
                transactionRepository.flush();

                Transaction transaction3 = new Transaction();
                transaction3.setOrder(order3);
                transaction3.setPayment(cod);
                transaction3.setTotal(new BigDecimal("5047.85"));
                transactionRepository.save(transaction3);
                transactionRepository.flush();

                Transaction transaction4 = new Transaction();
                transaction4.setOrder(order4);
                transaction4.setPayment(cod);
                transaction4.setTotal(new BigDecimal("5146.85"));
                transactionRepository.save(transaction4);
                transactionRepository.flush();

                Transaction transaction5 = new Transaction();
                transaction5.setOrder(order5);
                transaction5.setPayment(payAtShop);
                transaction5.setTotal(new BigDecimal("6493.85"));
                transactionRepository.save(transaction5);
                transactionRepository.flush();

                Transaction transaction6 = new Transaction();
                transaction6.setOrder(order6);
                transaction6.setPayment(payAtShop);
                transaction6.setTotal(new BigDecimal("7250.85"));
                transactionRepository.save(transaction6);
                transactionRepository.flush();

                Transaction transaction7 = new Transaction();
                transaction7.setOrder(order7);
                transaction7.setPayment(cod);
                transaction7.setTotal(new BigDecimal("9045.85"));
                transactionRepository.save(transaction7);
                transactionRepository.flush();

                Transaction transaction8 = new Transaction();
                transaction8.setOrder(order8);
                transaction8.setPayment(cod);
                transaction8.setTotal(new BigDecimal("9845.85"));
                transactionRepository.save(transaction8);
                transactionRepository.flush();

                Transaction transaction9 = new Transaction();
                transaction9.setOrder(order9);
                transaction9.setPayment(cod);
                transaction9.setTotal(new BigDecimal("10952.85"));
                transactionRepository.save(transaction9);
                transactionRepository.flush();

                Transaction transaction10 = new Transaction();
                transaction10.setOrder(order10);
                transaction10.setPayment(cod);
                transaction10.setTotal(new BigDecimal("1526.00"));
                transactionRepository.save(transaction10);
                transactionRepository.flush();

                Transaction transaction11 = new Transaction();
                transaction11.setOrder(order11);
                transaction11.setPayment(payAtShop);
                transaction11.setTotal(new BigDecimal("2766.00"));
                transactionRepository.save(transaction11);
                transactionRepository.flush();

                Transaction transaction12 = new Transaction();
                transaction12.setOrder(order12);
                transaction12.setPayment(payAtShop);
                transaction12.setTotal(new BigDecimal("897.00"));
                transactionRepository.save(transaction12);
                transactionRepository.flush();
            }
            // Insert feedbacks if not present
            if (feedbackRepository.count() == 0) {
                Feedback feedback1 = new Feedback();
                feedback1.setComment(
                        "I bought the 'Modern LED Pendant Light' for my kitchen remodel, and it exceeded my expectations. Not only does it provide excellent lighting, but its sleek design also complements the contemporary style of my kitchen perfectly. Very satisfied!");
                feedback1.setRate((short) 3);
                feedback1.setCreatedAt(Timestamp.valueOf("2024-04-02 16:40:06"));
                feedback1.setEmail("michael@gmail.com");
                feedback1.setName("Michael");
                feedbackRepository.save(feedback1);
                feedbackRepository.flush();

                Feedback feedback2 = new Feedback();
                feedback2.setComment(
                        "I've been searching for the perfect bedside lamps for ages, and I finally found them at UNIDECOR! The 'Vintage Glass Table Lamps' I purchased are stunning and add a cozy ambiance to my bedroom. Great selection and fast shipping!");
                feedback2.setRate((short) 3);
                feedback2.setCreatedAt(Timestamp.valueOf("2024-04-02 16:40:06"));
                feedback2.setEmail("emily@gmail.com");
                feedback2.setName("Emily");
                feedbackRepository.save(feedback2);
                feedbackRepository.flush();

                Feedback feedback3 = new Feedback();
                feedback3.setComment(
                        "I recently purchased the 'Elegant Crystal Chandelier' from UNIDECOR, and I'm absolutely thrilled with it! The quality is top-notch, and it adds a touch of glamour to my dining room. Highly recommend!");
                feedback3.setRate((short) 3);
                feedback3.setCreatedAt(Timestamp.valueOf("2024-04-02 16:40:06"));
                feedback3.setEmail("sarah@gmail.com");
                feedback3.setName("Sarah");
                feedbackRepository.save(feedback3);
                feedbackRepository.flush();

                Feedback feedback4 = new Feedback();
                feedback4.setComment("Very good");
                feedback4.setRate((short) 3);
                feedback4.setCreatedAt(Timestamp.valueOf("2024-04-02 16:40:06"));
                feedback4.setEmail("j97@gmail.com");
                feedback4.setName("Jack");
                feedbackRepository.save(feedback4);
                feedbackRepository.flush();
            }
            // Insert carts if not present
            if (cartRepository.count() == 0) {
                User user1 = userRepository.findAll().stream().filter(user -> user.getUsername().equals("hoangson123"))
                        .findFirst().orElse(null);
                User user2 = userRepository.findAll().stream().filter(user -> user.getUsername().equals("hoangson1234"))
                        .findFirst().orElse(null);
                User user3 = userRepository.findAll().stream().filter(user -> user.getUsername().equals("admin"))
                        .findFirst().orElse(null);

                Cart cart1 = new Cart();
                cart1.setUser(user2);
                cart1.setCreatedAt(Timestamp.valueOf("2024-04-05 04:31:08"));
                cartRepository.save(cart1);
                cartRepository.flush();

                Cart cart2 = new Cart();
                cart2.setUser(user1);
                cart2.setCreatedAt(Timestamp.valueOf("2024-04-05 07:29:13"));
                cartRepository.save(cart2);
                cartRepository.flush();

                Cart cart3 = new Cart();
                cart3.setUser(user3);
                cart3.setCreatedAt(Timestamp.valueOf("2024-05-13 02:02:24"));
                cartRepository.save(cart3);
                cartRepository.flush();
            }
            if (orderDetailRepository.count() == 0) {
                List<Orderdetail> orderDetails = Arrays.asList(
                        new Orderdetail(productRepository.findById(3L).orElse(null),
                                orderRepository.findById(13L).orElse(null), 2, new BigDecimal("598.00")),
                        new Orderdetail(productRepository.findById(25L).orElse(null),
                                orderRepository.findById(13L).orElse(null), 3, new BigDecimal("2397.00")),
                        new Orderdetail(productRepository.findById(41L).orElse(null),
                                orderRepository.findById(14L).orElse(null), 3, new BigDecimal("119.85")),
                        new Orderdetail(productRepository.findById(16L).orElse(null),
                                orderRepository.findById(15L).orElse(null), 2, new BigDecimal("538.00")),
                        new Orderdetail(productRepository.findById(19L).orElse(null),
                                orderRepository.findById(15L).orElse(null), 3, new BigDecimal("1197.00")),
                        new Orderdetail(productRepository.findById(15L).orElse(null),
                                orderRepository.findById(15L).orElse(null), 2, new BigDecimal("198.00")),
                        new Orderdetail(productRepository.findById(15L).orElse(null),
                                orderRepository.findById(16L).orElse(null), 1, new BigDecimal("99.00")),
                        new Orderdetail(productRepository.findById(8L).orElse(null),
                                orderRepository.findById(17L).orElse(null), 3, new BigDecimal("1347.00")),
                        new Orderdetail(productRepository.findById(3L).orElse(null),
                                orderRepository.findById(18L).orElse(null), 1, new BigDecimal("299.00")),
                        new Orderdetail(productRepository.findById(13L).orElse(null),
                                orderRepository.findById(18L).orElse(null), 2, new BigDecimal("458.00")));

                orderDetailRepository.saveAll(orderDetails);
            }
        } finally {

        }
    }

    private Order createOrder(User user, Orderstatus status, String date) {
        return createOrder(user, status, date, "Vũ", "Cường", "Thai Nguyen", "0365473859", null);
    }

    private Order createOrder(User user, Orderstatus status, String date, String note) {
        return createOrder(user, status, date, "Vũ", "Cường", "Thai Nguyen", "0365473859", note);
    }

    private Order createOrder(User user, Orderstatus status, String date, String firstName, String lastName,
            String address) {
        return createOrder(user, status, date, firstName, lastName, address, "0365473859", null);
    }

    private Order createOrder(User user, Orderstatus status, String date, String firstName, String lastName,
            String address, String phone, String note) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderstatus(status);
        order.setOrderDate(Timestamp.valueOf(date));
        order.setFirstname(firstName);
        order.setLastName(lastName);
        order.setAddress(address);
        order.setPhone(phone);
        order.setNotes(note);
        return order;
    }

    private Productstatus getSale() {
        return productStatusRepository.findAll().stream()
                .filter(status -> status.getName().equals("sale"))
                .findFirst().orElse(null);
    }
}