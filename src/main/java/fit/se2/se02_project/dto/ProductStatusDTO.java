package fit.se2.se02_project.dto;

public class ProductStatusDTO {
    private long id;
    private String name;

    public ProductStatusDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductStatusDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
