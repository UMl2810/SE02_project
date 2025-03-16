package fit.se2.se02_project.dto;

public class CategoryDTO {
    private long id;
    private String categoryName;
    private String description;
    private String image;

    public CategoryDTO(long id, String categoryName, String description, String image) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
        this.image = image;
    }

    public CategoryDTO(long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CategoryDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
