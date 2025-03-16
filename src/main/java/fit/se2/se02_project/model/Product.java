package fit.se2.se02_project.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "`product`")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "productName")
    private String productName;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;
    @Basic
    @Column(name = "isActive")
    private Byte isActive;
    @Basic
    @Column(name = "createAt")
    private Timestamp createAt;
    @Basic
    @Column(name = "createBy")
    private String createBy;
    @Basic
    @Column(name = "modifyAt")
    private Timestamp modifyAt;
    @Basic
    @Column(name = "modifyBy")
    private String modifyBy;
    @Basic
    @Column(name = "image")
    private String image;
    @Basic
    @Column(name = "saleprice")
    private BigDecimal saleprice;
    @OneToMany(mappedBy = "product")
    private Collection<Cartitem> cartitems;
    @OneToMany(mappedBy = "product")
    private Collection<Orderdetail> orderdetails;
    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "productStatusId", referencedColumnName = "id")
    private Productstatus productstatus;
    @OneToMany(mappedBy = "product")
    private Collection<Rate> rates;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Timestamp modifyAt) {
        this.modifyAt = modifyAt;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public BigDecimal getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(BigDecimal saleprice) {
        this.saleprice = saleprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (productName != null ? !productName.equals(product.productName) : product.productName != null) return false;
        if (title != null ? !title.equals(product.title) : product.title != null) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        if (quantity != null ? !quantity.equals(product.quantity) : product.quantity != null) return false;
        if (isActive != null ? !isActive.equals(product.isActive) : product.isActive != null) return false;
        if (createAt != null ? !createAt.equals(product.createAt) : product.createAt != null) return false;
        if (createBy != null ? !createBy.equals(product.createBy) : product.createBy != null) return false;
        if (modifyAt != null ? !modifyAt.equals(product.modifyAt) : product.modifyAt != null) return false;
        if (modifyBy != null ? !modifyBy.equals(product.modifyBy) : product.modifyBy != null) return false;
        if (image != null ? !image.equals(product.image) : product.image != null) return false;

        if (saleprice != null ? !saleprice.equals(product.saleprice) : product.saleprice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
        result = 31 * result + (modifyAt != null ? modifyAt.hashCode() : 0);
        result = 31 * result + (modifyBy != null ? modifyBy.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (saleprice != null ? saleprice.hashCode() : 0);
        return result;
    }

    public Collection<Cartitem> getCartitems() {
        return cartitems;
    }

    public void setCartitems(Collection<Cartitem> cartitems) {
        this.cartitems = cartitems;
    }

    public Collection<Orderdetail> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(Collection<Orderdetail> orderdetails) {
        this.orderdetails = orderdetails;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Productstatus getProductstatus() {
        return productstatus;
    }

    public void setProductstatus(Productstatus productstatus) {
        this.productstatus = productstatus;
    }

    public Collection<Rate> getRates() {
        return rates;
    }

    public void setRates(Collection<Rate> rates) {
        this.rates = rates;
    }
}
