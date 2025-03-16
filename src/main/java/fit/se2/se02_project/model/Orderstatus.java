package fit.se2.se02_project.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "`orderstatus`")
public class Orderstatus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "bootstapicon")
    private String bootstapicon;
    @OneToMany(mappedBy = "orderstatus")
    private Collection<Order> orders;

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

    public String getBootstapicon() {
        return bootstapicon;
    }

    public void setBootstapicon(String bootstapicon) {
        this.bootstapicon = bootstapicon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orderstatus that = (Orderstatus) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (bootstapicon != null ? !bootstapicon.equals(that.bootstapicon) : that.bootstapicon != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (bootstapicon != null ? bootstapicon.hashCode() : 0);
        return result;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }
}
