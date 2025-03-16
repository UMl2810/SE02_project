package fit.se2.se02_project.request;

import java.math.BigDecimal;

public class ProductQuantityRequest {
    private long productID;
    private int quantity;
    private BigDecimal price;

    public ProductQuantityRequest() {
    }

    public ProductQuantityRequest(long productID, int quantity, BigDecimal price) {
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
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
