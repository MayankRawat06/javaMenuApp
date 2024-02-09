package services;


import dto.Cart;
import dto.Order;
import dto.Product;
import dto.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

public class AdminMenu extends Menu {
    private final Scanner in = new Scanner(System.in);
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    // initial page for Admin
    public void home(User user) {
        log.log(Level.INFO, "User entered Admin Menu.");
        System.out.println("Select an Option below(1 to 4)");
        System.out.println("1.View Orders");
        System.out.println("2.View Existing Products");
        System.out.println("3.Add/Delete Products");
        System.out.println("4.Log Out");
        int choice = 0;
        try {
            choice = in.nextInt();
        } catch (Exception e) {
            log.log(Level.WARNING, "Invalid Input passed in User Menu.");
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            System.out.println(e.getMessage());
            home(user);
        }
        switch(choice) {
            case 1:
                System.out.println("Orders Page");
                viewAllOrders(user);
                System.out.println("Moving back to Home Menu.");
                home(user);
                break;
            case 2:
                productMenu();
                System.out.println("Moving back to Home Menu.");
                home(user);
                break;
            case 3:
                alterProducts(user);
                break;
            case 4:
                System.out.println("Logged Out Successfully.");
                start();
                break;
            default:
                log.log(Level.WARNING, "Invalid Input passed in User Menu.");
                System.out.println("Uh! Oh! Wrong Choice..");
                home(user);
        }
    }
    // add/delete products menu for admin
    public void alterProducts(User user) {
        log.log(Level.INFO, "Admin entered Alter Products Menu.");
        System.out.println("Select an Option below(1 to 3)");
        System.out.println("1.Add Product");
        System.out.println("2.Delete Product");
        System.out.println("3.Previous Page - Home");
        int choiceAlterProduct = 0;
        try {
            choiceAlterProduct = in.nextInt();
            in.nextLine();
        } catch (InputMismatchException i) {
            log.log(Level.WARNING, "Invalid Input passed in Alter Products Menu.");
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            alterProducts(user);
            in.nextLine();
            return;
        }
        switch(choiceAlterProduct) {
            case 1:
                getProductToAdd(user);
                System.out.println("Redirecting back to Alter Products Menu");
                alterProducts(user);
                break;
            case 2:
                getProductToDelete(user);
                System.out.println("Redirecting back to Alter Products Menu");
                alterProducts(user);
                break;
            case 3:
                System.out.println("Redirecting back to Home Menu");
                home(user);
                break;
            default:
                log.log(Level.WARNING, "Invalid Input passed in Alter Products Menu.");
                System.out.println("Uh! Oh! Wrong Choice..");
                alterProducts(user);
        }
    }
    // fetch product id
    public int fetchProductID() {
        System.out.println("Enter Product ID");
        int productID = 0;
        try {
            productID = in.nextInt();
        } catch (Exception e) {
            log.log(Level.WARNING, "Invalid Input passed as product ID in Add Product Menu.");
            System.out.println("Invalid Character, Please use Integers!");
            productID = fetchProductID();
        }
        if(productID <= 0) {
            log.log(Level.WARNING, "Invalid Input passed as product ID in Add Product Menu.");
            System.out.println("Product ID cannot be negative or equal to zero. Please Try again.");
            productID = fetchProductID();
        }
        return productID;
    }
    //  fetch description
    public String fetchDescription() {
        System.out.println("Enter the Description of Product.");
        String description = in.nextLine();
        return description;
    }
    // fetch category
    public String fetchCategory() {
        System.out.println("Enter the Category.");
        String category = in.nextLine();
        return category;
    }
    // fetch quantity available
    public int fetchQuantityAvailable() {
        System.out.println("Enter quantity available");
        int quantity = 0;
        try {
            quantity = in.nextInt();
        } catch (Exception e) {
            log.log(Level.WARNING, "Invalid Input passed as quantity available in Add Product Menu.");
            System.out.println("Invalid Character, Please use Integers!");
            quantity = fetchQuantityAvailable();
        }
        if(quantity <= 0) {
            log.log(Level.WARNING, "Invalid Input passed as quantity available in Add Product Menu.");
            System.out.println("Quantity cannot be negative or equal to zero. Please Try again.");
            quantity = fetchQuantityAvailable();
        }
        return quantity;
    }
    // fetch price
    public int fetchPrice() {
        System.out.println("Enter the price of product");
        int price = 0;
        try {
            price = in.nextInt();
        } catch (Exception e) {
            log.log(Level.WARNING, "Invalid Input passed as price in Add Product Menu.");
            System.out.println("Invalid Character, Please use Integers!");
            price = fetchPrice();
        }
        if(price <= 0) {
            log.log(Level.WARNING, "Invalid Input passed as price in Add Product Menu.");
            System.out.println("price cannot be negative or equal to zero. Please Try again.");
            price = fetchPrice();
        }
        return price;
    }
    // add product menu
    public void getProductToAdd(User user) {
        log.log(Level.INFO, "Admin entered Add Product Menu.");
        int productID = fetchProductID();
        if(productService.getProductByID(productID) != null ) {
            log.log(Level.WARNING, "Product with same product ID already exists.Retrying.");
            System.out.println("Product with same product ID already exists. Please Try Again!");
            getProductToAdd(user);
            return;
        }
        in.nextLine();
        String description = fetchDescription();
        String category = fetchCategory();
        int quantityAvailable = fetchQuantityAvailable();
        int price = fetchPrice();
        in.nextLine();
        productService.addProduct(productID, description, quantityAvailable, price, category);
        log.log(Level.INFO, "Product added successfully.");
        System.out.println("Product Added Successfully.");
    }
    // delete product menu
    public void getProductToDelete(User user) {
        log.log(Level.INFO, "Admin entered Add Product Menu.");
        int productID = fetchProductID();
        if(productService.getProductByID(productID) == null ) {
            log.log(Level.WARNING, "Product with given product ID doesn't exists.Retrying.");
            System.out.println("Product with same product ID doesn't exists. Please Try Again!");
            getProductToDelete(user);
            return;
        }
        productService.deleteProduct(productID);
        log.log(Level.INFO, "Product deleted successfully.");
        System.out.println("Product Deleted Successfully.");
    }
    public void viewAllOrders(User user) {
        log.log(Level.INFO, "Admin entered View all Orders Menu.");
        Map<String, ArrayList<Order>> ordersWithIDs = orderService.getAllOrders();
        if(ordersWithIDs.isEmpty()) {
            System.out.println("It's Empty in here.");
            return;
        }
        ordersWithIDs.forEach((id, orders) -> printIDWithOrders(id, orders));
    }
    public void printIDWithOrders(String emailID, ArrayList<Order> orders) {
        System.out.println("Email ID: " + emailID);
        printOrders(orders);
    }
    public void printOrders(ArrayList<Order> orders) {
        if(orders == null) {
            System.out.println("It's empty in here.");
            return;
        }
        orders.forEach((order) -> printOrder(order));
    }
    public void printOrder(Order order) {
        Cart cart = order.getCart();
        int orderValue = order.getOrderValue();
        String recipient = order.getReceiverMail();
        String address = order.getAddress().getAddr();
        printCart(cart);
        System.out.format("%-15s %25s %15s", "Recipient EmailID", "Delivery Address", "Amount Payable(in INR)\n");
        System.out.format("%-15s %25s %15s", recipient, address, orderValue + "\n");
    }
    public void printCart(Cart cart) {
        cart.getCart().forEach((k,v) -> printProductInCart(k, v));
    }
    public void printProductInCart(Product product, int quantity) {
        System.out.format("%-15s %15s %15s", product.getDescription(), quantity, product.getPrice() + "\n");
    }
}