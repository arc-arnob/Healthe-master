package com.covid_booking.covid.model;

import java.util.List;

public class Addresscontainer {
    
    private List<Address> addresses;


    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getStreetName() {
        return addresses.get(0).getStreetName();
    }

    

    public String getMunicipality() {
        return addresses.get(0).getMunicipality();
    }

   

    public String getCountrySubdivisionName() {
        return addresses.get(0).getCountrySubdivisionName();
    }

    

    public String getPostalCode() {
        return addresses.get(0).getPostalCode();
    }

    

    public String getCountry() {
        return addresses.get(0).getCountry();
    }

}
