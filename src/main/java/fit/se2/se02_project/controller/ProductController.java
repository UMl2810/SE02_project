package fit.se2.se02_project.controller;

import fit.se2.se02_project.dto.CategoryDTO;
import fit.se2.se02_project.dto.ProductDTO;
import fit.se2.se02_project.dto.ProductStatusDTO;
import fit.se2.se02_project.model.Category;
import fit.se2.se02_project.model.Product;
import fit.se2.se02_project.model.Productstatus;
import fit.se2.se02_project.model.User;
import fit.se2.se02_project.repositories.CategoryRepository;
import fit.se2.se02_project.repositories.ProductRepository;
import fit.se2.se02_project.repositories.ProductStatusRepository;
import fit.se2.se02_project.request.AddproductRequest;
import fit.se2.se02_project.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductStatusRepository productStatusRepository;
    @Autowired
    private CommonService commonService;

    @GetMapping("/index")
    public String index(Model model) {
        User currentUser = commonService.getCurrentUser();
        List<ProductDTO> products = productRepository.findAll()
                .stream()
                .filter(p -> p.getIsActive() == 1)
                .map(this::toProductDTO)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        model.addAttribute("user", currentUser);
        return "listProduct";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        User currentUser = commonService.getCurrentUser();
        if (currentUser == null || currentUser.getRole().getId() != 1) {
            return "redirect:/auth/login";
        }
        List<ProductDTO> products = productRepository.findAll()
                .stream()
                .filter(p -> p.getIsActive() == 1)
                .map(this::toProductDTO)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "adminproduct";
    }

    @GetMapping("/update/{productId}")
    public String update(Model model, @PathVariable Long productId, @RequestParam(required = false) Map<String, String> message) {
        User currentUser = commonService.getCurrentUser();
        if (currentUser == null || currentUser.getRole().getId() != 1) {
            return "redirect:/auth/login";
        }
        ProductDTO product = productRepository.findAll()
                .stream()
                .filter(p -> p.getIsActive() == 1 && p.getId() == productId)
                .map(this::toProductDTO)
                .findAny()
                .orElse(null);
        List<CategoryDTO> categories = categoryRepository.findAll()
                .stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());
        List<ProductStatusDTO> productStatus = productStatusRepository.findAll()
                .stream()
                .map(this::toProductStatusDTO)
                .collect(Collectors.toList());
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("productStatus", productStatus);
        model.addAttribute("message", message);
        return "editproduct";
    }
    @PostMapping("/update/{productId}")
    public String update(Model model, @PathVariable Long productId, @ModelAttribute AddproductRequest request) {
        User currentUser = commonService.getCurrentUser();
        Map<String, String> errorList = new HashMap<>();
        if (request.getProductName() == null || request.getProductName().isEmpty()) {
            errorList.put("NullProductName", "Product name cannot be empty");
        }
        if (request.getQuantity() == null) {
            errorList.put("NullQuantity", "Quantity cannot be empty");
        }
        if (request.getPrice() == null) {
            errorList.put("NullPrice", "Price cannot be empty");
        }
        if (!errorList.isEmpty()) {
            return "redirect:/product/update/" + productId + "?" + buildQueryString(errorList);
        }
        Product newProduct = productRepository.findById(productId).orElse(null);
        newProduct.setProductName(request.getProductName());
        newProduct.setQuantity(Integer.parseInt(request.getQuantity()));
        newProduct.setPrice(new BigDecimal(request.getPrice()));
        newProduct.setProductstatus(productStatusRepository.findById(request.getProductStatusId()).orElse(null));
        if (request.getSaleprice() == null || request.getSaleprice().isEmpty()) {
            newProduct.setSaleprice(null);
        } else {
            newProduct.setSaleprice(new BigDecimal(request.getSaleprice()));
        }
        newProduct.setCategory(categoryRepository.findById(request.getCategoryId()).orElse(null));
        newProduct.setDescription(request.getDescription());
        newProduct.setModifyAt(new Timestamp(System.currentTimeMillis()));
        newProduct.setModifyBy(currentUser.getUsername());
        MultipartFile imageFile = request.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = timestamp + "_" + imageFile.getOriginalFilename();
            String directoryPath = "src/main/resources/static/img/lightType";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String filePath = Paths.get(directoryPath, filename).toString();
            try {
                Files.write(Paths.get(filePath), imageFile.getBytes());
                String imageUrl = "/img/lightType/" + filename;
                newProduct.setImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        productRepository.save(newProduct);
        Map<String, String> success = new HashMap<>();
        success.put("Success", "Update product successfully");

        return "redirect:/product/update/" + productId + "?" + buildQueryString(success);
    }

    @GetMapping("/add")
    public String add(Model model, @RequestParam(required = false) Map<String, String> message) {
        User currentUser = commonService.getCurrentUser();
        if (currentUser == null || currentUser.getRole().getId() != 1) {
            return "redirect:/auth/login";
        }
        List<CategoryDTO> categories = categoryRepository.findAll()
                .stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());
        List<ProductStatusDTO> productStatus = productStatusRepository.findAll()
                .stream()
                .map(this::toProductStatusDTO)
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("productStatus", productStatus);
        model.addAttribute("message", message);
        return "addproduct";
    }

    @PostMapping("/add")
    public String add(Model model, @ModelAttribute AddproductRequest request) {
        User currentUser = commonService.getCurrentUser();
        Map<String, String> errorList = new HashMap<>();
        if (request.getProductName() == null || request.getProductName().isEmpty()) {
            errorList.put("NullProductName", "Product name cannot be empty");
        }
        if (request.getQuantity() == null) {
            errorList.put("NullQuantity", "Quantity cannot be empty");
        }
        if (request.getPrice() == null) {
            errorList.put("NullPrice", "Price cannot be empty");
        }
        if (!errorList.isEmpty()) {
            return "redirect:/product/add?" + buildQueryString(errorList);
        }
        Product newProduct = new Product();
        newProduct.setProductName(request.getProductName());
        newProduct.setQuantity(Integer.parseInt(request.getQuantity()));
        newProduct.setPrice(new BigDecimal(request.getPrice()));
        newProduct.setProductstatus(productStatusRepository.findById(request.getProductStatusId()).orElse(null));
        if (request.getSaleprice() == null || request.getSaleprice().isEmpty()) {
            newProduct.setSaleprice(null);
        } else {
            newProduct.setSaleprice(new BigDecimal(request.getSaleprice()));
        }
        newProduct.setCategory(categoryRepository.findById(request.getCategoryId()).orElse(null));
        newProduct.setDescription(request.getDescription());
        newProduct.setIsActive((byte) 1);
        newProduct.setCreateAt(new Timestamp(System.currentTimeMillis()));
        newProduct.setCreateBy(currentUser.getUsername());
        MultipartFile imageFile = request.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = timestamp + "_" + imageFile.getOriginalFilename();
            String directoryPath = "src/main/resources/static/img/lightType";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String filePath = Paths.get(directoryPath, filename).toString();
            try {
                Files.write(Paths.get(filePath), imageFile.getBytes());
                String imageUrl = "/img/lightType/" + filename;
                newProduct.setImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        productRepository.save(newProduct);
        Map<String, String> success = new HashMap<>();
        success.put("Success", "Add product successfully");

        return "redirect:/product/add?" + buildQueryString(success);
    }



    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        User currentUser = commonService.getCurrentUser();
        ProductDTO product = productRepository.findAll()
                .stream()
                .filter(p -> p.getIsActive() == 1 && p.getId() == id)
                .map(this::toProductDTO)
                .findAny()
                .orElse(null);
        ;
        model.addAttribute("product", product);
        model.addAttribute("user", currentUser);
        return "detailProduct";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("productId") Long productId){
        Product product = productRepository.findById(productId).orElse(null);
        product.setIsActive((byte) 0);
        productRepository.save(product);

        return "redirect:/product/admin";
    }


    private String buildQueryString(Map<String, String> params) {
        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            queryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return queryString.toString();
    }

    private ProductDTO toProductDTO(Product product) {
        return new ProductDTO(product.getId(),
                product.getProductName(),
                product.getCategory().getId(),
                product.getProductstatus().getId(),
                product.getCategory().getCategoryName(),
                product.getProductstatus().getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getSaleprice(),
                product.getImage(),
                product.getDescription());
    }

    private CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getCategoryName());
    }

    private ProductStatusDTO toProductStatusDTO(Productstatus productstatus) {
        return new ProductStatusDTO(productstatus.getId(), productstatus.getName());
    }
}
