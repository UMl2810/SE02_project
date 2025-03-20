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

            if (userRepository.count() == 0) {
                Role adminRole = roleRepository.findAll().stream().filter(role -> role.getName().equals("Admin")).findFirst().orElse(null);
                Role userRole = roleRepository.findAll().stream().filter(role -> role.getName().equals("User")).findFirst().orElse(null);

                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("263fec58861449aacc1c328a4aff64aff4c62df4a2d50b3f207fa89b6e242c9aa778e7a8baeffef85b6ca6d2e7dc16ff0a760d59c13c238f6bcdc32f8ce9cc62");
                admin.setEmail("no.replyunidecor@gmail.com");
                admin.setIsActive((byte) 1);
                admin.setRole(adminRole);
                userRepository.save(admin);

                User user1 = new User();
                user1.setUsername("cuong_test");
                user1.setPassword("e13efc991a9bf44bbb4da87cdbb725240184585ccaf270523170e008cf2a3b85f45f86c3da647f69780fb9e971caf5437b3d06d418355a68c9760c70a31d05c7");
                user1.setEmail("test@gmail.com");
                user1.setIsActive((byte) 1);
                user1.setRole(userRole);
                userRepository.save(user1);

                User user2 = new User();
                user2.setUsername("thaitest");
                user2.setPassword("e13efc991a9bf44bbb4da87cdbb725240184585ccaf270523170e008cf2a3b85f45f86c3da647f69780fb9e971caf5437b3d06d418355a68c9760c70a31d05c7");
                user2.setEmail("thai@gmail.com");
                user2.setIsActive((byte) 1);
                user2.setRole(userRole);
                userRepository.save(user2);
            }
            if (categoryRepository.count() == 0) {
                Category category1 = new Category();
                category1.setCategoryName("chair");
                category1.setDescription("For a charming and versatile seating option, explore our collection of benches, perfect for adding a touch of elegance to any room.");
                category1.setIsActive((byte) 1);
                categoryRepository.save(category1);
                categoryRepository.flush();

                Category category2 = new Category();
                category2.setCategoryName("sofa");
                category2.setDescription("Indulge in comfort and style with our exquisite range of sofas, designed to transform your living room into a haven of relaxation.");
                category2.setIsActive((byte) 1);
                categoryRepository.save(category2);
                categoryRepository.flush();

                Category category3 = new Category();
                category3.setCategoryName("table");
                category3.setDescription("Discover the ideal table for every occasion, from elegant dining to functional workspaces, each crafted with quality and style in mind.");
                category3.setIsActive((byte) 1);
                categoryRepository.save(category3);
                categoryRepository.flush();

                Category category4 = new Category();
                category4.setCategoryName("decor");
                category4.setDescription("Elevate your home's aesthetic with our curated selection of decoration pieces, each one a unique expression of artistry and design.\n");
                category4.setIsActive((byte) 1);
                categoryRepository.save(category4);
                categoryRepository.flush();

                Category category5 = new Category();
                category5.setCategoryName("bed");
                category5.setDescription("Experience the ultimate in restful slumber with our luxurious beds, designed to create your personal sanctuary of comfort.");
                category5.setIsActive((byte) 1);
                categoryRepository.save(category5);
                categoryRepository.flush();

                Category category6 = new Category();
                category6.setCategoryName("storage");
                category6.setDescription("Maximize your space and declutter with our innovative storage solutions, blending practicality with sophisticated style.");
                category6.setIsActive((byte) 1);
                categoryRepository.save(category6);
                categoryRepository.flush();
            }

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

            if (productRepository.count() < 32) {
                Map<String, Category> categories = categoryRepository.findAll().stream().collect(Collectors.toMap(Category::getCategoryName, category -> category));

                Productstatus saleStatus = getSale();

                String[][] products = {
                        {"Simple brown leather sofa", "399.00", "sofa", "299.00", "/img/productImg/Sofa01.jpg"},
                        {"Patterned leather sofa", "499.00", "sofa", "379.00", "/img/productImg/Sofa02.jpg"},
                        {"Crocodile leather sofa", "289.00", "sofa", "219.00", "/img/productImg/Sofa03.jpg"},
                        {"European pattern sofa", "199.00", "sofa", "149.00", "/img/productImg/Sofa04.jpg"},
                        {"Asian pattern sofa", "249.00", "sofa", "189.00", "/img/productImg/Sofa05.jpg"},
                        {"Royal sofa", "279.00", "sofa", "209.00", "/img/productImg/Sofa06.jpg"},
                        {"Royal bed", "349.00", "bed", "299.00", "/img/productImg/bed.jpg"},
                        {"Simple black and white bed", "299.00", "bed", "249.00", "/img/productImg/bed2.png"},
                        {"Black queen bed", "39.00", "bed", "29.00", "/img/productImg/bed3.png"},
                        {"King size bed", "529.00", "bed", "419.00", "/img/productImg/bed4.png"},
                        {"Silky bed", "359.00", "bed", "279.00", "/img/productImg/bed5.png"},
                        {"Low back chair", "489.00", "chair", "379.00", "/img/productImg/chair (1).jpg"},
                        {"Clove wood dining chair", "369.00", "chair", "319.00", "/img/productImg/chair (2).jpg"},
                        {"Relaxing king chair", "319.00", "chair", "269.00", "/img/productImg/chair (3).jpg"},
                        {"Simple dining chair", "379.00", "chair", "329.00", "/img/productImg/chair (4).jpg"},
                        {"Straight back chair", "49.00", "chair", "39.00", "/img/productImg/chair (5).jpg"},
                        {"Stylized plastic chair", "99.00", "chair", "79.00", "/img/productImg/chair (6).jpg"},
                        {"Modern sandalwood glass cabinet", "89.00", "storage", "69.00", "/img/productImg/storage (1).jpg"},
                        {"Vietnamese antique wooden cabinets", "59.00", "storage", "49.00", "/img/productImg/storage (2).jpg"},
                        {"Stylized jewelry cabinet", "79.00", "storage", "59.00", "/img/productImg/storage (3).jpg"},
                        {"Wardrobe of the future", "369.00", "storage", "289.00", "/img/productImg/storage (4).jpg"},
                        {"Simple wardrobe", "389.00", "storage", "339.00", "/img/productImg/storage (5).jpg"},
                        {"Royal wall pendulum clock", "49.00", "decor", "39.00", "/img/productImg/decor.jpg"},
                        {"Full cover headboard painting", "79.00", "decor", "59.00", "/img/productImg/decor2.png"},
                        {"Small flower vase", "69.00", "decor", "49.00", "/img/productImg/decor3.png"},
                        {"Roman wall clock", "79.00", "decor", "59.00", "/img/productImg/decor4.jpg"},
                        {"Large pendulum clock", "549.00", "decor", "439.00", "/img/productImg/decor.png"},
                        {"Large dining table", "69.00", "table", "49.00", "/img/productImg/table.jpg"},
                        {"Small living room table", "69.00", "table", "49.00", "/img/productImg/table2.png"},
                        {"Ebony coffee table", "599.00", "table", "479.00", "/img/productImg/table3.jpg"},
                        {"single leg table", "329.00", "table", "249.00", "/img/productImg/table4.jpg"}};

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
                User user1 = userRepository.findAll().stream().filter(user -> user.getUsername().equals("cuong_test")).findFirst().orElse(null);
                User user2 = userRepository.findAll().stream().filter(user -> user.getUsername().equals("cuong_test")).findFirst().orElse(null);

                Map<String, Orderstatus> statusMap = orderStatusRepository.findAll().stream().collect(Collectors.toMap(Orderstatus::getName, status -> status));

                List<Order> orders = Arrays.asList(
                        createOrder(user1, statusMap.get("Wait for confirmation"), "2025-03-21 11:00:00"),
                        createOrder(user1, statusMap.get("Canceled"), "2025-03-21 12:00:00"),
                        createOrder(user1, statusMap.get("Canceled"), "2024-04-06 09:39:20"),
                        createOrder(user1, statusMap.get("Accomplished"), "2025-03-21 22:00:00"),
                        createOrder(user1, statusMap.get("Wait for confirmation"), "2025-03-21 23:00:00"),
                        createOrder(user1, statusMap.get("Wait for delivery"), "2024-04-08 03:18:23"),
                        createOrder(user1, statusMap.get("Wait for confirmation"), "2024-04-12 06:04:34", "Giao gio hanh chinh"),
                        createOrder(user1, statusMap.get("Wait for delivery"), "2025-04-12 06:07:56", "office hours"),
                        createOrder(user1, statusMap.get("Wait for confirmation"), "2025-04-13 09:37:24", "office hours"),
                        createOrder(user2, statusMap.get("Canceled"), "2025-05-13 02:43:56", "Vu Ngoc", "Cuong", "Ha Noi"),
                        createOrder(user2, statusMap.get("Wait for confirmation"), "2025-05-13 02:47:35", "Vu Ngoc", "Cuong", "Ha Noi"),
                        createOrder(user1, statusMap.get("Canceled"), "2025-05-14 02:29:38", "Vu Ngoc", "Cuong", "Ha Noi"));
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

                Payment cod = paymentRepository.findAll().stream().filter(payment -> payment.getName().equals("COD")).findFirst().orElse(null);
                Payment payAtShop = paymentRepository.findAll().stream().filter(payment -> payment.getName().equals("Pay at shop")).findFirst().orElse(null);

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
                feedback1.setComment("Very satisfied!");
                feedback1.setRate((short) 3);
                feedback1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                feedback1.setEmail("Cuong@gmail.com");
                feedback1.setName("Cuong");
                feedbackRepository.save(feedback1);
                feedbackRepository.flush();

                Feedback feedback2 = new Feedback();
                feedback2.setComment("Great selection and fast shipping!");
                feedback2.setRate((short) 3);
                feedback2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                feedback2.setEmail("Thai@gmail.com");
                feedback2.setName("Thai");
                feedbackRepository.save(feedback2);
                feedbackRepository.flush();

                Feedback feedback3 = new Feedback();
                feedback3.setComment("Highly recommend!");
                feedback3.setRate((short) 3);
                feedback3.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                feedback3.setEmail("Cong@gmail.com");
                feedback3.setName("Cong");
                feedbackRepository.save(feedback3);
                feedbackRepository.flush();

                Feedback feedback4 = new Feedback();
                feedback4.setComment("greatttttt");
                feedback4.setRate((short) 3);
                feedback4.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                feedback4.setEmail("vncuong2810@gmail.com");
                feedback4.setName("Cuong");
                feedbackRepository.save(feedback4);
                feedbackRepository.flush();
            }
            // Insert carts if not present
            if (cartRepository.count() == 0) {
                User user1 = userRepository.findAll().stream().filter(user -> user.getUsername().equals("cuong_test")).findFirst().orElse(null);
                User user2 = userRepository.findAll().stream().filter(user -> user.getUsername().equals("thaitest")).findFirst().orElse(null);
                User user3 = userRepository.findAll().stream().filter(user -> user.getUsername().equals("admin")).findFirst().orElse(null);

                Cart cart1 = new Cart();
                cart1.setUser(user2);
                cart1.setCreatedAt(Timestamp.valueOf("2025-03-05 04:31:08"));
                cartRepository.save(cart1);
                cartRepository.flush();

                Cart cart2 = new Cart();
                cart2.setUser(user1);
                cart2.setCreatedAt(Timestamp.valueOf("2025-03-11 07:29:13"));
                cartRepository.save(cart2);
                cartRepository.flush();

                Cart cart3 = new Cart();
                cart3.setUser(user3);
                cart3.setCreatedAt(Timestamp.valueOf("2025-03-13 02:02:24"));
                cartRepository.save(cart3);
                cartRepository.flush();
            }
            if (orderDetailRepository.count() == 0) {
                List<Orderdetail> orderDetails = Arrays.asList(
                        new Orderdetail(productRepository.findById(3L).orElse(null), orderRepository.findById(13L).orElse(null), 2, new BigDecimal("598.00")),
                        new Orderdetail(productRepository.findById(25L).orElse(null), orderRepository.findById(13L).orElse(null), 3, new BigDecimal("2397.00")),
                        new Orderdetail(productRepository.findById(41L).orElse(null), orderRepository.findById(14L).orElse(null), 3, new BigDecimal("119.85")),
                        new Orderdetail(productRepository.findById(16L).orElse(null), orderRepository.findById(15L).orElse(null), 2, new BigDecimal("538.00")),
                        new Orderdetail(productRepository.findById(19L).orElse(null), orderRepository.findById(15L).orElse(null), 3, new BigDecimal("1197.00")),
                        new Orderdetail(productRepository.findById(15L).orElse(null), orderRepository.findById(15L).orElse(null), 2, new BigDecimal("198.00")),
                        new Orderdetail(productRepository.findById(15L).orElse(null), orderRepository.findById(16L).orElse(null), 1, new BigDecimal("99.00")),
                        new Orderdetail(productRepository.findById(8L).orElse(null), orderRepository.findById(17L).orElse(null), 3, new BigDecimal("1347.00")),
                        new Orderdetail(productRepository.findById(3L).orElse(null), orderRepository.findById(18L).orElse(null), 1, new BigDecimal("299.00")),
                        new Orderdetail(productRepository.findById(13L).orElse(null), orderRepository.findById(18L).orElse(null), 2, new BigDecimal("458.00")));
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

    private Order createOrder(User user, Orderstatus status, String date, String firstName, String lastName, String address) {
        return createOrder(user, status, date, firstName, lastName, address, "0365473859", null);
    }

    private Order createOrder(User user, Orderstatus status, String date, String firstName, String lastName, String address, String phone, String note) {
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
        return productStatusRepository.findAll().stream().filter(status -> status.getName().equals("sale")).findFirst().orElse(null);
    }
}