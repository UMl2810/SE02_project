package fit.se2.se02_project.dto;

public class CountCategoryDTO {
    private String categoryName;
    private long id;
    private int total;

    public CountCategoryDTO() {
    }

    public CountCategoryDTO(String categoryName, long id, int total) {
        this.categoryName = categoryName;
        this.id = id;
        this.total = total;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
