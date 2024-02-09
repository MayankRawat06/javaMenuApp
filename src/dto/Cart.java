package dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<Product, Integer> cart = new HashMap<>();
    int totalPrice = 0;

    public Cart() {
    }

    public Cart(Map<Product, Integer> cart, int totalPrice) {
        this.cart = cart;
        this.totalPrice = totalPrice;
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }
    public int getTotalPrice() {
        return totalPrice;
    }
    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void addProductToCart(Product product, int quantity) {
        if(!this.cart.containsKey(product)) {
            this.cart.put(product, quantity);
        }
        else this.cart.put(product, this.cart.get(product) + quantity);
    }
}
