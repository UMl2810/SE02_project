package fit.se2.se02_project.dto;

public class CountProductDTO {
    private int count;
    private String productName;
    private long productId;

    public CountProductDTO() {
    }

    public CountProductDTO(int count, String productName, long productId) {
        this.count = count;
        this.productName = productName;
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
