package com.covid_booking.covid.model;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;



public class ResultsAddress {

    private String streetName;
    private String municipality;
    private String countrySubdivisionName;
    private String postalCode;
    private String country;
    private Double lat;
    private Double log;

    @JsonProperty("address")
    public void unpackNestedAddress(Map<String,Object> address){
        //Map<String,Object> place = (Map<String,Object>)address.get("address");

        this.streetName = (String)address.get("streetName");
        this.municipality = (String)address.get("municipality");
        this.countrySubdivisionName = (String)address.get("countrySubdivisionName");
        this.postalCode = (String)address.get("postalCode");
        this.country = (String)address.get("country");


     }
     @JsonProperty("position")
     public void unpackNestedPosition(Map<String,Double> position){
         this.lat = position.get("lat");
         this.log = position.get("lon");
     }


     public String getStreetName() {
        return streetName;
    }

   

    public String getMunicipality() {
        return municipality;
    }

    

    public String getCountrySubdivisionName() {
        return countrySubdivisionName;
    }

    

    public String getPostalCode() {
        return postalCode;
    }

   
    public String getCountry() {
        return country;
    }
    public Double getLat(){
        return lat;
    }
    public Double getLon(){
        return log;
    }
}