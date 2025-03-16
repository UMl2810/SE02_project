package fit.se2.se02_project.request;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class AddproductRequest {
    private String productName;
    private String quantity;
    private String price;
    private long productStatusId;
    private String saleprice;
    private long categoryId;
    private MultipartFile image;
    private String description;


    public AddproductRequest(String productName, String quantity, String price, long productStatusId, String saleprice, long categoryId, MultipartFile image, String description) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.productStatusId = productStatusId;
        this.saleprice = saleprice;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
    }

    public AddproductRequest() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getProductStatusId() {
        return productStatusId;
    }

    public void setProductStatusId(long productStatusId) {
        this.productStatusId = productStatusId;
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
