import dto.Product;
import services.ProductService;
import services.Menu;
import utility.DataSerializationHelper;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        ArrayList<Product> products = DataSerializationHelper.deserializeData("products.ser");
        if(products != null) {
            ProductService.setAllProducts(products);
        }
        menu.start();
    }
}