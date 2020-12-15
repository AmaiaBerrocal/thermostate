package com.thermostate.dominio;

public class User {
    int id;
    String name;
    String password;
    String salt;

    public User(int id, String name, String password, String salt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salt = salt;
    }

    public User(String name, String password, String salt) {
        this.name = name;
        this.password = password;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


}
