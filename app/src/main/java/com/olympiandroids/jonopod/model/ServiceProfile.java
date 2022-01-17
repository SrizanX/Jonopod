package com.olympiandroids.jonopod.model;

public class ServiceProfile {
    private String UUID;
    private String imageLink;
    private String name;
    private String sector;
    private String designation;
    private String phone;
    private UserLocation location;

    public ServiceProfile() {
    }

    public ServiceProfile(String imageLink, String name, String sector, String designation, String phone, UserLocation location) {
        this.imageLink = imageLink;
        this.name = name;
        this.sector = sector;
        this.designation = designation;
        this.phone = phone;
        this.location = location;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserLocation getLocation() {
        return location;
    }

    public void setLocation(UserLocation location) {
        this.location = location;
    }
}
