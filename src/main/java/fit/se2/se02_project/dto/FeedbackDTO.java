package fit.se2.se02_project.dto;

public class FeedbackDTO {
    private long id;
    private String name;
    private String comment;
    private String email;
    private Short rate;

    public FeedbackDTO(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public FeedbackDTO(String name, String comment, String email, Short rate) {
        this.name = name;
        this.comment = comment;
        this.email = email;
        this.rate = rate;
    }

    public FeedbackDTO(long id, String name, String comment, String email, Short rate) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.email = email;
        this.rate = rate;
    }

    public FeedbackDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getRate() {
        return rate;
    }

    public void setRate(Short rate) {
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
