package fit.se2.se02_project.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "`feedback`")
public class Feedback {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "rate")
    private Short rate;
    @Basic
    @Column(name = "createAt")
    private Timestamp createdAt;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Short getRate() {
        return rate;
    }

    public void setRate(Short rate) {
        this.rate = rate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        if (id != feedback.id) return false;
        if (comment != null ? !comment.equals(feedback.comment) : feedback.comment != null) return false;
        if (rate != null ? !rate.equals(feedback.rate) : feedback.rate != null) return false;
        if (createdAt != null ? !createdAt.equals(feedback.createdAt) : feedback.createdAt != null) return false;
        if (email != null ? !email.equals(feedback.email) : feedback.email != null) return false;
        if (name != null ? !name.equals(feedback.name) : feedback.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
