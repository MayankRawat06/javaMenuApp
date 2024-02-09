package services;

import dto.Category;
import dto.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductService {
    public static ArrayList<Product> products = new ArrayList<>();
    static {
        products.add(new Product(1, "TV", 30, 16000, new Category("Electronics")));
        products.add(new Product(2, "bike", 20, 123000, new Category("Vehicles")));
        products.add(new Product(3, "Refrigerator", 15, 43000, new Category("Electronics")));
    }
    public void listProductsUnsorted() {
        listProducts(products);
    }
    public static Comparator<Product> sortProductsByPriceAsc = new Comparator<>() {
        public int compare(Product p1, Product p2) {

            int price1 = p1.getPrice();
            int price2 = p2.getPrice();

            // For ascending order
            return price1 - price2;

        }
    };
    public static Comparator<Product> sortProductsByPriceDesc = new Comparator<>() {
        public int compare(Product p1, Product p2) {

            int price1 = p1.getPrice();
            int price2 = p2.getPrice();

            // For descending order
            return price2 - price1;
        }
    };
    public void listProductsByPriceAsc() {
        products.sort(sortProductsByPriceAsc);
        listProducts(products);
    }
    public void listProductsByPriceDesc() {
        products.sort(sortProductsByPriceDesc);
        listProducts(products);
    }
    public void listProducts(ArrayList<Product> products) {
        if(products.isEmpty()) {
            System.out.println("No products Found!");
            return;
        }
        System.out.format("%-15s %15s %15s %15s %15s", "Category", "Product ID", "Description", "Price(in INR)", "Quantity Available\n");
        for(Product p : products) {
            System.out.format("%-15s %15s %15s %15s %15s", p.getCategory().getName(), p.getProductID(), p.getDescription(), p.getPrice(), p.getQuantityAvailable() + "\n");
        }
    }
    public void applyFilterByCategory(String categoryName) {
        ArrayList<Product> filteredProductsByCategory = products.stream().filter(p -> p.getCategory().getName().equals(categoryName)).collect(Collectors.toCollection(ArrayList::new));
        listProducts(filteredProductsByCategory);
    }
    public Product getProductByID(int id) {
        for(Product p : products) {
            if(p.getProductID() == id) return p;
        }
        return null;
    }
    public void addProduct(int productID, String description, int quantityAvailable, int price, String categoryName) {
        products.add(new Product(productID, description, quantityAvailable, price, new Category(categoryName)));
    }
    public void deleteProduct(int productID) {
        products.remove(getProductByID(productID));
    }
    public static ArrayList<Product> getAllProducts() {
        return products;
    }
    public static void setAllProducts(ArrayList<Product> productsList) {
        products = productsList;
    }
}
