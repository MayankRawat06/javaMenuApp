package dto;


import java.io.Serializable;

public class Product implements Serializable {
    int productID;
    Category category;
    String description;
    int quantityAvailable;
    int price;
    public Product(int productID, String description, int quantityAvailable, int price, Category category) {
        this.productID = productID;
        this.description = description;
        this.quantityAvailable = quantityAvailable;
        this.price = price;
        this.category = category;
    }
    public int getProductID() {
        return productID;
    }
    public String getDescription() {
        return description;
    }
    public int getQuantityAvailable() {
        return quantityAvailable;
    }
    public int getPrice() {
        return price;
    }
    public Category getCategory() {
        return category;
    }
    public void setProductID(int productID) {
        this.productID = productID;
    }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}
