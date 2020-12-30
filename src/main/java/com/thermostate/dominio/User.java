package com.thermostate.dominio;

public class User {
    int id;
    String name;
    String password;
    String salt;
    Integer activationDate;
    boolean active;

    public User(int id, String name, String password, String salt, Integer activationDate, boolean active) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.activationDate = activationDate;
        this.active = active;
    }

    public User(String name, String password, String salt, Integer activationDate, boolean active) {
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.activationDate = activationDate;
        this.active = active;
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

    public Integer getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Integer activationDate) {
        this.activationDate = activationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
