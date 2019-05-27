package com.developer.aaleen.passwordgenerator;

/**
 * Created by Aaleen on 12/27/2017.
 */

public class PasswordModel {

    String Name, Password;

    public PasswordModel(String name, String password) {
        Name = name;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
