package dto;

import services.*;

public class User {
    String name;
    private String emailID;
    private String password;
    boolean isAdmin = false;
    int age;
    public User(String name, String emailID, String password, int age) {
        this.name = name;
        this.emailID = emailID;
        this.password = password;
        this.age = age;
    }
    public User(String name, String emailID, String password, int age, boolean isAdmin) {
        this.name = name;
        this.emailID = emailID;
        this.password = password;
        this.age = age;
        this.isAdmin = isAdmin;
    }
    public String getName() {
        return name;
    }
    public String getEmailID() {
        return emailID;
    }
    public String getPassword() {
        return password;
    }
    public boolean getIsAdmin() {
        return isAdmin;
    }
    public int getAge() {
        return age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
