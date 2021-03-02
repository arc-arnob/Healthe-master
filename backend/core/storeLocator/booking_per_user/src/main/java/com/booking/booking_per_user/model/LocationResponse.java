package com.booking.booking_per_user.model;

import jdk.jfr.DataAmount;


public class LocationResponse {

    private String storeName;
    private String streetName;
    private Double distance;
    private String metric;

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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public LocationResponse(String storeName, String streetName, Double distance, String metric) {
        this.storeName = storeName;
        this.streetName = streetName;
        this.distance = distance;
        this.metric = metric;
    }

	public LocationResponse() {
	}

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }
    
}
