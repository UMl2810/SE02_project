package fit.se2.se02_project.controller;

import fit.se2.se02_project.dto.CartItemDTO;
import fit.se2.se02_project.model.*;
import fit.se2.se02_project.repositories.CartItemsRepository;
import fit.se2.se02_project.repositories.CartRepository;
import fit.se2.se02_project.repositories.PaymentRepository;
import fit.se2.se02_project.repositories.ProductRepository;
import fit.se2.se02_project.request.AddtoCartRequest;
import fit.se2.se02_project.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CommonService commonService;
    @GetMapping("/index")
    public String index(Model model, @RequestParam(required = false) String alertMessage, @RequestParam(required = false) String orderSuccess) {
        User user = commonService.getCurrentUser();
        model.addAttribute("user", user);
        if (user == null){
            return "redirect:/auth/login";
        }
        Cart userCart = cartRepository.findAll()
                .stream()
                .filter(c -> c.getUser().getId() == user.getId())
                .findAny()
                .orElse(null);
        if (userCart == null) {
            userCart = new Cart();
            userCart.setUser(user);
            userCart.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            cartRepository.save(userCart);
        }
        Cart finalUserCart = userCart;
        List<CartItemDTO> productsInCart = cartItemsRepository.findAll()
                .stream()
                .filter(ci -> ci.getCart().getId() == finalUserCart.getId())
                .map(this::toCartItemDTO)
                .collect(Collectors.toList());
        BigDecimal total = cartRepository.getTotal(userCart.getId());
        List<Payment> payments = paymentRepository.findAll();
        model.addAttribute("products", productsInCart);
        model.addAttribute("total", total);
        model.addAttribute("payments", payments);
        model.addAttribute("AlertMessage", alertMessage);
        model.addAttribute("OrderSuccess", orderSuccess);
        return "cart";
    }

    @PostMapping("/add")
    public String add(AddtoCartRequest request, Model model){
        try {
            User user = commonService.getCurrentUser();
            if (user == null){
                return "redirect:/auth/login";
            }
            Optional<Cart> optionalCart  = cartRepository.findAll()
                    .stream()
                    .filter(c -> c.getUser().getId() == user.getId())
                    .findAny();

            Cart userCart;
            if (optionalCart.isEmpty()) {
                userCart = new Cart();
                userCart.setUser(user);
                userCart.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                cartRepository.save(userCart);
            } else {
                userCart = optionalCart.get();
            }

            if (cartItemsRepository.findAll()
                    .stream()
                    .anyMatch(ci -> ci.getCart().getId() == userCart.getId() && ci.getProduct().getId() == request.getProductId())) {
                String message = "Product has already in cart";
                return "redirect:/cart/index?alertMessage=" + message;
            }
            Product product = productRepository.findAll()
                    .stream()
                    .filter(p -> p.getId() == request.getProductId())
                    .findAny()
                    .orElse(null);
            Cartitem newCartItem = new Cartitem();
            newCartItem.setCart(userCart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(request.getQuantity());
            cartItemsRepository.save(newCartItem);

            return "redirect:/cart/index";
        } catch (Exception ex) {
            model.addAttribute("error", "An error occurred: " + ex.getMessage());
            return "redirect:/error";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam ("productId") Long productId) {
        try {
            User user = commonService.getCurrentUser();
            if (user == null){
                return "redirect:/auth/login";
            }
            Cart userCart = cartRepository.findAll()
                    .stream()
                    .filter(c -> c.getUser().getId() == user.getId())
                    .findAny()
                    .orElse(null);
            Cartitem product = cartItemsRepository.findAll()
                    .stream()
                    .filter(p -> p.getProduct().getId() == productId && p.getCart().getId() == userCart.getId())
                    .findAny()
                    .orElse(null);
           if (product != null){
               cartItemsRepository.delete(product);
           }
            return "redirect:/cart/index";
        } catch (Exception ex) {
            return "redirect:/error";
        }
    }

    @GetMapping("/up/{id}")
    public String up(@PathVariable Long id) {
        try {
            Cartitem cartItem = cartItemsRepository .findById(id)
                    .orElse(null);
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemsRepository.save(cartItem);
            return "redirect:/cart/index";
        } catch (Exception ex) {
            return "redirect:/error/index";
        }
    }

    @GetMapping("/down/{id}")
    public String down(@PathVariable Long id) {
        try {
            Cartitem cartItem = cartItemsRepository .findById(id)
                    .orElse(null);
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemsRepository.save(cartItem);
            return "redirect:/cart/index";
        } catch (Exception ex) {
            return "redirect:/error/index";
        }
    }



    private CartItemDTO toCartItemDTO(Cartitem cartitem) {
        BigDecimal price = cartitem.getProduct().getSaleprice() != null ?
                (cartitem.getProduct().getSaleprice().multiply(BigDecimal.valueOf(cartitem.getQuantity()))) :
                (cartitem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartitem.getQuantity())));

        return new CartItemDTO(cartitem.getId(),
                cartitem.getProduct().getId(),
                cartitem.getProduct().getProductName(),
                cartitem.getQuantity(),
                price,
                cartitem.getProduct().getImage());
    }

}
