package com.webShop.webShop.models;

public class UserLogin {

    private String email;
    private String password;

    public UserLogin() {
    }

    public UserLogin(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
