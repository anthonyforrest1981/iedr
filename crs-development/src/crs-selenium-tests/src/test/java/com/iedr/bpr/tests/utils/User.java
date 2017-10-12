package com.iedr.bpr.tests.utils;

public class User {
    public String login;
    public String password;
    public boolean isRegistrar;
    public String email;

    public User(String login, String password, boolean isRegistrar, String email) {
        this.login = login;
        this.password = password;
        this.isRegistrar = isRegistrar;
        this.email = email;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.isRegistrar = false;
        this.email = null;
    }
}
