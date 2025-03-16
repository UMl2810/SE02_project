package fit.se2.se02_project.request;

public class AddtoCartRequest {
    private long productId;
    private int quantity;

    public AddtoCartRequest() {
    }

    public AddtoCartRequest(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
