package fit.se2.se02_project.dto;

import java.util.List;

public class ListOrderDTO {
    private long id;
    private List<OrderProductDTO> listProduct;
    private long statusId;
    private String statusName;
    private long userId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;

    public ListOrderDTO() {
    }

    public ListOrderDTO(long id, List<OrderProductDTO> listProduct, long statusId, String statusName, long userId, String firstName, String lastName, String address, String email) {
        this.id = id;
        this.listProduct = listProduct;
        this.statusId = statusId;
        this.statusName = statusName;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<OrderProductDTO> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<OrderProductDTO> listProduct) {
        this.listProduct = listProduct;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
