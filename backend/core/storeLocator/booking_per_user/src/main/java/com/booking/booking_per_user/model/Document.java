package com.booking.booking_per_user.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Document {

    private String storeName;
    private String streetName;
    private Location location;

    public Document(String storeName, Location location, String streetName) {
        this.storeName = storeName;
        this.location = location;
        this.streetName =  streetName;
    
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Document() {
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    
    
    
}
