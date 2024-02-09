package dto;

public class Order {
    private String receiverMail;
    private Cart cart;
    private Address address;
    private int OrderValue;

    public Order(String receiverMail, Cart cart, Address address, int orderValue) {
        this.receiverMail = receiverMail;
        this.cart = cart;
        this.address = address;
        OrderValue = orderValue;
    }

    public String getReceiverMail() {
        return receiverMail;
    }

    public void setReceiverMail(String receiverMail) {
        this.receiverMail = receiverMail;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getOrderValue() {
        return OrderValue;
    }

    public void setOrderValue(int orderValue) {
        OrderValue = orderValue;
    }
}
