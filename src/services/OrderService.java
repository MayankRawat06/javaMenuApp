package services;



import dto.Address;
import dto.Cart;
import dto.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderService {
    private final static Map<String, ArrayList<Order>> ordersWithID = new HashMap<>();
    public void addUserOrder(String id, Order order) {
        ArrayList<Order> orders = ordersWithID.get(id);
        if(orders == null) {
            ordersWithID.put(id, new ArrayList<>());
        }
        ordersWithID.get(id).add(order);
    }
    public void addToOrder(String id, Cart cart) {
        Address address = new Address("Store Pick up");
        Cart orderCart = new Cart(cart.getCart(), cart.getTotalPrice());
        Order order = new Order(id, orderCart, address, orderCart.getTotalPrice());
        cart.setCart(new HashMap<>());
        cart.setTotalPrice(0);
        addUserOrder(id, order);
    }
    public ArrayList<Order> getUserOrders(String id) {
        if(!ordersWithID.containsKey(id)) return null;
        return ordersWithID.get(id);
    }
    public Map<String, ArrayList<Order>> getAllOrders() {
        return ordersWithID;
    }
}
