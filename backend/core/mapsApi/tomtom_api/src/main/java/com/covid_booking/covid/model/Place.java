package com.covid_booking.covid.model;

public class Place {
    
    private String streetName;
    private String municipality;
    private String countrySubdivisionName;
    private String postalCode;
    private String country;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getCountrySubdivisionName() {
        return countrySubdivisionName;
    }

    public void setCountrySubdivisionName(String countrySubdivisionName) {
        this.countrySubdivisionName = countrySubdivisionName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Place() {
    }

    public Place(String streetName, String municipality, String countrySubdivisionName, String postalCode,
            String country) {
        this.streetName = streetName;
        this.municipality = municipality;
        this.countrySubdivisionName = countrySubdivisionName;
        this.postalCode = postalCode;
        this.country = country;
    }

}
