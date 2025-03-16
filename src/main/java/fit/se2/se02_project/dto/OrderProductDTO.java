package fit.se2.se02_project.dto;

import java.math.BigDecimal;

public class OrderProductDTO {
    private String image;
    private String productName;
    private int quantity;
    private BigDecimal price;

    public OrderProductDTO() {
    }

    public OrderProductDTO(String image, String productName, int quantity, BigDecimal price) {
        this.image = image;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
