package services;

import dto.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    public List<User> users = new ArrayList<>();
    public UserService() {
        this.users.add(new User("admin","admin@ecom.com", "Admin321@", 21, true));
        this.users.add(new User("Mayank","mayank@gg.com", "M@yank14", 21));
        this.users.add(new User("test","test@gmail.com", "test", 21));
    }
    public User register(User newUser) {
        users.add(newUser);
        return newUser;
    }
    public boolean authenticate(String emailID, String password) {
        for(User user : users) {
            if(user.getEmailID().equals(emailID) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public boolean isAdmin(String emailID) {
        for(User user : users) {
            if(user.getEmailID().equals(emailID)) {
                return user.getIsAdmin();
            }
        }
        return false;
    }
    public User getUser(String emailID) {
        for(User user : users) {
            if(user.getEmailID().equals(emailID)) {
                return user;
            }
        }
        return null;
    }
}
