package services;


import dto.*;

import java.util.*;
import java.util.Scanner;
import java.util.logging.Level;

public class UserMenu extends Menu {
    private final Scanner in = new Scanner(System.in);
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final AddressService addressService = new AddressService();
    private final CartService cartService = new CartService();
    // initial page for user
    public void home(User user) {
        log.log(Level.INFO, "User entered User Menu.");
        System.out.println("Select an Option below(1 to 6)");
        System.out.println("1.Products");
        System.out.println("2.View Cart");
        System.out.println("3.View Orders");
        System.out.println("4.Payment Methods");
        System.out.println("5.Manage Addresses");
        System.out.println("6.Log Out");
        int choice = 0;
        try {
            choice = in.nextInt();
            in.nextLine();
        } catch (InputMismatchException i) {
            log.log(Level.WARNING, "Invalid Input passed in User Menu.");
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            in.nextLine();
            home(user);
            return;
        }
        switch(choice) {
            case 1:
                productMenu();
                offerToBuy(user);
                break;
            case 2:
                viewCart(user);
                Cart cart = cartService.getUserCart(user.getEmailID());
                if(cart != null && !cart.getCart().isEmpty()) {
                    askCheckout(user);
                }
                System.out.println("Moving back to Home.");
                home(user);
                break;
            case 3:
                System.out.println("Orders Page");
                viewOrders(user);
                System.out.println("Moving back to Home");
                home(user);
                break;
            case 4:
                System.out.println("Payments Page");
                break;
            case 5:
                addresses(user);
                System.out.println("Moving back to Home");
                home(user);
                break;
            case 6:
                log.log(Level.INFO, "User logged out successfully. Moving to Main Menu.");
                System.out.println("Logged Out Successfully.");
                start();
                return;
            default:
                log.log(Level.WARNING, "Invalid Input passed in User Menu.");
                System.out.println("Uh! Oh! Wrong Choice..");
                home(user);
        }
    }
    // give user offer to buy - if they want to buy something
    public void offerToBuy(User user) {
        System.out.println("Do you want to buy something?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        int buyChoice = 0;
        try {
            buyChoice = in.nextInt();
            in.nextLine();
        }
        catch (Exception e) {
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            in.nextLine();
            offerToBuy(user);
            return;
        }
        switch(buyChoice) {
            case 1:
                addToCartMenu(user);
                break;
            case 2:
                System.out.println("Moving back to Home");
                home(user);
                break;
            default:
                System.out.println("Uh! Oh! Wrong Choice..");
                offerToBuy(user);
        }
    }
    // addresses page
    public void addresses(User user) {
        log.log(Level.INFO, "User entered Addresses Menu.");
        System.out.println("Addresses Page");
        System.out.println("Select an Option below(1 to 3)");
        System.out.println("1.View Addresses");
        System.out.println("2.Add Address");
        System.out.println("3.Previous Page - Home");
        int choiceAddress = 0;
        try {
            choiceAddress = in.nextInt();
            in.nextLine();
        } catch (InputMismatchException i) {
            log.log(Level.WARNING, "Invalid Input passed in Addresses Menu.");
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            addresses(user);
            in.nextLine();
            return;
        }
        switch(choiceAddress) {
            case 1:
                viewAddresses(user);
                System.out.println("Redirecting back to Addresses Menu");
                addresses(user);
                break;
            case 2:
                if(addAddressMenu(user))
                    System.out.println("Address added Successfully");
                else
                    System.out.println("Uh, Oh! Address can't be added, Please Try again!");
                System.out.println("Redirecting back to Addresses Menu");
                addresses(user);
                break;
            case 3:
                System.out.println("Redirecting back to home Menu");
                home(user);
                break;
            default:
                log.log(Level.WARNING, "Invalid Input passed in Addresses Menu.");
                System.out.println("Uh! Oh! Wrong Choice..");
                addresses(user);
        }
    }
    // addresses - add address page
    public boolean addAddressMenu(User user) {
        log.log(Level.INFO, "User entered add address Menu.");
        in.nextLine();
        String emailID = user.getEmailID();
        System.out.println("Enter the address");
        String addr = in.nextLine();
        Address newAddr = new Address(addr);
        return addressService.addAddress(emailID, newAddr);
    }
    // addresses - view address page
    public void viewAddresses(User user) {
        log.log(Level.INFO, "User entered view address Menu.");
        in.nextLine();
        String emailID = user.getEmailID();
        ArrayList<Address> addressList = addressService.getAddressesByEmail(emailID);
        if(addressList.isEmpty()) {
            System.out.println("No saved Addresses");
            return;
        }
        System.out.println("Your saved Addresses");
        for(Address addr : addressList) {
            System.out.println(addr.getAddr());
        }
    }
    // fetch product ID from user
    public int fetchProductID() {
        System.out.println("Enter Product ID");
        int productID = 0;
        try {
            productID = in.nextInt();
        } catch (Exception e) {
            log.log(Level.WARNING, "Invalid Input passed as product ID in Add to Cart Menu.");
            System.out.println("Invalid Character, Please use Integers!");
            in.nextLine();
            productID = fetchProductID();
        }
        if(productID <= 0) {
            log.log(Level.WARNING, "Invalid Input passed as product ID in Add to Cart Menu.");
            System.out.println("Product ID cannot be negative or equal to zero. Please Try again.");
            productID = fetchProductID();
        }
        return productID;
    }
    // fetch quantity from user
    public int fetchQuantity() {
        System.out.println("Enter Quantity");
        int quantity = 0;
        try {
            quantity = in.nextInt();
        } catch (Exception e) {
            log.log(Level.WARNING, "Invalid Input passed as quantity in Add to Cart Menu.");
            in.nextLine();
            System.out.println("Invalid Character, Please use Integers!");
            quantity = fetchQuantity();
        }
        return quantity;
    }
    // add to cart menu
    public void addToCartMenu(User user) {
        log.log(Level.INFO, "User entered Add to Cart Menu.");
        System.out.println("Enter Product ID and Qty to add to cart");
        int productID = fetchProductID();
        Product product = productService.getProductByID(productID);
        if(product == null) {
            log.log(Level.WARNING, "Wrong product ID input by user.");
            System.out.println("Product Doesn't Exist. Please Try again");
            addToCartMenu(user);
        }
        else {
            if(product.getQuantityAvailable() <= 0) {
                log.log(Level.WARNING, "Product Out of Stock.");
                System.out.println("Product is Out of Stock.");
                addToCartMenu(user);
            }
            else {
                int quantity = fetchQuantity();
                int quantityAvailable = product.getQuantityAvailable();
                if(quantity <= 0 || quantity > quantityAvailable) {
                    log.log(Level.WARNING, "Wrong quantity input by user.");
                    System.out.println("Quantity cannot be less than or equal to 0 or more than " + quantityAvailable);
                    quantity = fetchQuantity();
                }
                log.log(Level.INFO, "Product added to cart successfully.");
                cartService.addToCart(user.getEmailID(), product, quantity);
                System.out.println(product.getDescription() + "(s) added to cart successfully.");
            }
        }
        System.out.println("Cart Status");
        viewCart(user);
        addMoreProductsMenu(user);
    }
    // ask if user want to add more products
    public void addMoreProductsMenu(User user) {
        System.out.println("Select from Options(1 to 3)");
        System.out.println("1.Add More Products");
        System.out.println("2.Move ahead with Cart");
        System.out.println("3.Previous Page - Home");
        int addMoreChoice = 0;
        try {
            addMoreChoice = in.nextInt();
            in.nextLine();
        }
        catch(Exception e) {
            log.log(Level.WARNING, "Invalid Input passed in Add More Products Menu.");
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            in.nextLine();
            addToCartMenu(user);
            return;
        }
        switch (addMoreChoice) {
            case 1:
                productMenu();
                addToCartMenu(user);
                break;
            case 2:
                askCheckout(user);
                home(user);
                break;
            case 3:
                home(user);
                break;
            default:
                log.log(Level.WARNING, "Invalid Input passed in Add More Products Menu.");
                System.out.println("Uh! Oh! Wrong Choice..");
                addToCartMenu(user);
        }
    }
    // view cart
    public void viewCart(User user) {
        log.log(Level.INFO, "User entered view cart Menu.");
        Cart userCart = cartService.getUserCart(user.getEmailID());
        if(userCart == null || userCart.getCart().isEmpty()) {
            System.out.println("It's empty in here.");
            return;
        }
        printCart(userCart);
        System.out.println("Total Price: " + userCart.getTotalPrice());
    }
    // print cart in proper format
    public void printCart(Cart cart) {
        System.out.format("%-15s %15s %15s", "Product", "Quantity", "Price(in INR)\n");
        cart.getCart().forEach((k,v) -> printProductInCart(k, v));
    }
    // print each product in cart
    public void printProductInCart(Product product, int quantity) {
        System.out.format("%-15s %15s %15s", product.getDescription(), quantity, product.getPrice() + "\n");
    }
    // view all orders
    public void viewOrders(User user) {
        log.log(Level.INFO, "User entered Orders Menu.");
        ArrayList<Order> orders = orderService.getUserOrders(user.getEmailID());
        if(orders == null) {
            System.out.println("It's empty in here.");
            return;
        }
        orders.forEach((order) -> printOrder(order));
    }
    // print each order in proper format
    public void printOrder(Order order) {
        Cart cart = order.getCart();
        int orderValue = order.getOrderValue();
        String recipient = order.getReceiverMail();
        String address = order.getAddress().getAddr();
        printCart(cart);
        System.out.format("%-15s %25s %15s", "Recipient EmailID", "Delivery Address", "Amount Payable(in INR)\n");
        System.out.format("%-15s %25s %15s", recipient, address, orderValue + "\n");
    }
    // ask for checkout from user
    public void askCheckout(User user) {
        log.log(Level.INFO, "User entered Ask Checkout Menu.");
        System.out.println("Select an Option below(1 to 2)");
        System.out.println("1.Check Out");
        System.out.println("2.Previous Page - Home");
        int askCheckoutChoice = 0;
        try {
            askCheckoutChoice = in.nextInt();
            in.nextLine();
        } catch(Exception e) {
            log.log(Level.WARNING, "Invalid Input passed in Ask Checkout Menu.");
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            in.nextLine();
            home(user);
            return;
        }
        switch (askCheckoutChoice) {
            case 1:
                orderService.addToOrder(user.getEmailID(), cartService.getUserCart(user.getEmailID()));
                log.log(Level.INFO, "Order placed Successfully.");
                System.out.println("Order placed Successfully!!");
                break;
            case 2:
                System.out.println("Moving back to Home.");
                home(user);
                break;
            default:
                log.log(Level.WARNING, "Invalid Input passed in Ask Checkout Menu.");
                System.out.println("Uh! Oh! Wrong Choice..");
                askCheckout(user);
        }
    }
}