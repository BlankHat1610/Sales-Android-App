package dev.mxt.banhang.model;

public class ShoppingCart {
    private int id;
    private String name;
    private int price;
    private String image;
    private String brand;
    private int quantity;

    public ShoppingCart(int id, String name, int price, String image, String brand, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.brand = brand;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
