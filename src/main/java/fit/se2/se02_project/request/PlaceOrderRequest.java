package fit.se2.se02_project.request;

import java.util.List;

public class PlaceOrderRequest {
    private List<ProductQuantityRequest> productInfor;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private int payment;
    private String note;

    public PlaceOrderRequest() {
    }

    public PlaceOrderRequest(List<ProductQuantityRequest> productInfor, String firstName, String lastName, String address, String phone, int payment, String note) {
        this.productInfor = productInfor;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.payment = payment;
        this.note = note;
    }

    public List<ProductQuantityRequest> getProductInfor() {
        return productInfor;
    }

    public void setProductInfor(List<ProductQuantityRequest> productInfor) {
        this.productInfor = productInfor;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
