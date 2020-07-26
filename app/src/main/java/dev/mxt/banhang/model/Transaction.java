package dev.mxt.banhang.model;

public class Transaction {
    private int userId;
    private int totalPrice;
    private String note;
    private String deliveryAddress;
    private String deliveryPhone;
    private String cartItemJson;

    public Transaction(int userId, int totalPrice, String note, String deliveryAddress, String deliveryPhone, String cartItemJson) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.note = note;
        this.deliveryAddress = deliveryAddress;
        this.deliveryPhone = deliveryPhone;
        this.cartItemJson = cartItemJson;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getCartItemJson() {
        return cartItemJson;
    }

    public void setCartItemJson(String cartItemJson) {
        this.cartItemJson = cartItemJson;
    }
}
