package fit.se2.se02_project.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "`transaction`")
public class Transaction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "total")
    private BigDecimal total;
    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "paymentID", referencedColumnName = "id")
    private Payment payment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
