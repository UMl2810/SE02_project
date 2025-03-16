package fit.se2.se02_project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "`cartitem`")
public class Cartitem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "quantity")
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "cartID", referencedColumnName = "id")
    private Cart cart;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cartitem cartitem = (Cartitem) o;

        if (id != cartitem.id) return false;
        if (quantity != null ? !quantity.equals(cartitem.quantity) : cartitem.quantity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
