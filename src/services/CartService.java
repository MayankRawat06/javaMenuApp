package services;

import dto.Address;
import dto.Cart;
import dto.Product;
import dto.User;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartService {
    private final Map<String, Cart> cartsWithID = new LinkedHashMap<>();
    public void addUserCart(String id, Cart cart) {
        cartsWithID.put(id, cart);
    }
    public void addToCart(String id, Product product, int qty) {
        Cart userCart = getUserCart(id);
        if(userCart == null)
            userCart = new Cart();
        product.setQuantityAvailable(product.getQuantityAvailable() - qty);
        userCart.addProductToCart(product, qty);
        userCart.setTotalPrice(userCart.getTotalPrice() + product.getPrice() * qty);
        addUserCart(id, userCart);
    }
    public Cart getUserCart(String id) {
        if(!cartsWithID.containsKey(id)) return null;
        return cartsWithID.get(id);
    }
}
