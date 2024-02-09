package services;

import dto.User;
import dto.Product;
import utility.DataSerializationHelper;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;
import java.util.regex.*;
import java.util.Scanner;


// class for two views - User and Admin
public class Menu {
    private final UserService userService = new UserService();
    private final ProductService productService = new ProductService();
    LogManager logManager = LogManager.getLogManager();
    Logger log = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final Scanner in = new Scanner(System.in);
    // main menu
    public void start() {
        log.log(Level.INFO, "Application started.");
        System.out.println("Select an Option below(1 to 4)");
        System.out.println("1.Register");
        System.out.println("2.Login");
        System.out.println("3.Products");
        System.out.println("4.Exit");
        int choiceProfile = 0;
        try {
            choiceProfile = in.nextInt();
            in.nextLine();
        } catch (Exception e) {
            log.log(Level.WARNING, "Invalid Input passed in main menu.");
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            in.nextLine();
            start();
            return;
        }
        switch(choiceProfile) {
            case 1:
                User user = registerMenu();
                if(user != null) {
                    System.out.println("Account created Successfully");
                    log.log(Level.INFO, "Account creation successful.");
                    UserMenu userMenu = new UserMenu();
                    userMenu.home(user);
                }
                break;
            case 2:
                if(!loginMenu()) {
                    System.out.println("Uh, Oh! Account Login unsuccessful, Please Try again!");
                }
                break;
            case 3:
                System.out.println("Products Page. Kindly Log in to Buy");
                productMenu();
                System.out.println("Moving back to Main Menu.");
                start();
                break;
            case 4:
                System.out.println("Thank you and have a nice day.");
                log.log(Level.INFO, "Program Exited.");
                DataSerializationHelper.serializeData(ProductService.getAllProducts(), "products.ser");
                break;
            default:
                log.log(Level.WARNING, "Invalid Input passed in main menu.");
                System.out.println("Uh! Oh! Wrong Choice..");
                start();
        }
    }
    public String fetchEmail() {
        System.out.print("Enter your email ID:  ");
        String emailID = in.nextLine();
        emailID = emailID.toLowerCase();
        if(!isValidEmailAddress(emailID)) {
            log.log(Level.WARNING, "Invalid Input passed as Email.");
            System.out.println("Invalid Email address, Please try again!");
            emailID = fetchEmail();
        }
        return emailID;
    }
    public int fetchAge() {
        System.out.print("Enter your age  ");
        int age = 0;
        try {
            age = in.nextInt();
        } catch (Exception e) {
            log.log(Level.WARNING, "Invalid Input passed as Age in Register Menu.");
            System.out.println("Invalid Character, Please use Integers!");
            age = fetchAge();
        }
        if(age < 0) {
            log.log(Level.WARNING, "Invalid Input passed as Age in Register Menu.");
            System.out.println("Age cannot be negative or equal to zero. Please Try again.");
            age = fetchAge();
        }
        return age;
    }
    public String fetchPassword() {
        System.out.print("Enter Password: ");
        String password = in.nextLine();
        if(!isValidPassword(password)) {
            log.log(Level.WARNING, "Invalid Input passed as password.");
            System.out.println("Password must contain at least 8 characters with at least one uppercase, one lowercase, one digit and one special character.");
            System.out.println("Please try again.");
            password = fetchPassword();
        }
        return password;
    }
    // register page
    public User registerMenu() {
        log.log(Level.INFO, "User entered Register Menu.");
        String emailID = fetchEmail();
        User user = userService.getUser(emailID);
        if(user != null) {
            log.log(Level.INFO, "User already exists. In register menu.");
            System.out.println("This email ID is already registered. Kindly log in.");
            System.out.println("Moving to Login Menu");
            loginMenu();
            return null;
        }
        System.out.println("Enter your name");
        String name = in.nextLine();
        int age = fetchAge();
        in.nextLine();
        String password = fetchPassword();
        boolean isAdmin = false;
        User newUser = new User(name, emailID, password, age, isAdmin);
        return userService.register(newUser);
    }
    // login page
    public boolean loginMenu() {
        log.log(Level.INFO, "User entered Login Menu.");
        String emailID = fetchEmail();
        String password = fetchPassword();
        if(!userService.authenticate(emailID, password)) {
            log.log(Level.WARNING, "Account Login Unsuccessful. Invalid Credentials. Retrying");
            System.out.println("Oops! Wrong Credentials, Please Try Again.");
            return loginMenu();
        }
        else {
            log.log(Level.INFO, "Account login successful.");
            System.out.println("Account Login Successfully");
            redirectToMenu(emailID);
        }
        return true;
    }
    // redirect to either admin or user menu based on credentials.
    public void redirectToMenu(String emailID) {
        User user = userService.getUser(emailID);
        if(userService.isAdmin(emailID)) {
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.home(user);
        }
        else {
            UserMenu userMenu = new UserMenu();
            userMenu.home(user);
        }
    }
    // utility function to check if email is valid -- returns true if valid
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    // Function to validate the password.
    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }
    // products menu
    public void productMenu() {
        log.log(Level.INFO, "User directed to Product menu.");
        System.out.println("Select an Option below(1 to 5)");
        System.out.println("1.View Products");
        System.out.println("2.Sort Products by Price");
        System.out.println("3.Filter Products by Category");
        System.out.println("4.Previous Page - Main");
        int productViewChoice = 0;
        try {
            productViewChoice = in.nextInt();
            in.nextLine();
        } catch (Exception e) {
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            in.nextLine();
            productMenu();
            return;
        }
        switch (productViewChoice) {
            case 1:
                System.out.println("Products");
                productService.listProductsUnsorted();
                break;
            case 2:
                sortMenu();
                break;
            case 3:
                categoryMenu();
                break;
            case 4:
                System.out.println("Redirecting back to Main Menu");
                start();
                break;
            default:
                System.out.println("Uh! Oh! Wrong Choice..");
                productMenu();
        }
    }
    public void sortMenu() {
        log.log(Level.INFO, "User entered product sort view menu");
        System.out.println("Select an Option below(1 to 3)");
        System.out.println("1.Low to High");
        System.out.println("2.High to Low");
        System.out.println("3.Previous Page - Products");
        int sortChoice = 0;
        try {
            sortChoice = in.nextInt();
        } catch (Exception e) {
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            sortMenu();
        }
        switch (sortChoice) {
            case 1:
                productService.listProductsByPriceAsc();
                break;
            case 2:
                productService.listProductsByPriceDesc();
                break;
            case 3:
                System.out.println("Redirecting back to Product Menu");
                productMenu();
                break;
            default:
                System.out.println("Uh! Oh! Wrong Choice..");
                sortMenu();
        }
    }
    // category menu
    public void categoryMenu() {
        log.log(Level.INFO, "User in filter by category menu.");
        System.out.println("Select a Category to Apply Filter");
        System.out.println("1.Electronics");
        System.out.println("2.Vehicles");
        System.out.println("3.Previous Page - Products Menu");
        int categoryChoice = 0;
        try {
            categoryChoice = in.nextInt();
        } catch (Exception e) {
            System.out.println("Uh, Oh! Invalid character, Please Try again!");
            categoryMenu();
        }
        switch (categoryChoice) {
            case 1:
                System.out.println("Filtering Products by Electronics");
                productService.applyFilterByCategory("Electronics");
                break;
            case 2:
                System.out.println("Filtering Products by Vehicles");
                productService.applyFilterByCategory("Vehicles");
                break;
            case 5:
                System.out.println("Redirecting back to Products Menu");
                productMenu();
                break;
            default:
                System.out.println("Uh! Oh! Wrong Choice..");
                categoryMenu();
        }
    }
}
