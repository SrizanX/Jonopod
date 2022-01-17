package com.olympiandroids.jonopod.model;

public class User {
    private String UID;
    private String name;
    private String email;
    private String password;
    private String gender;

    public User() {
    }

    public User(String UID, String name, String email, String password, String gender) {
        this.UID = UID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
