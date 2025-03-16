package fit.se2.se02_project.request;

public class FeedbackRequest {
    private String rate;
    private String name;
    private String email;
    private String comment;

    public FeedbackRequest() {
    }

    public FeedbackRequest(String rate, String name, String email, String comment) {
        this.rate = rate;
        this.name = name;
        this.email = email;
        this.comment = comment;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
