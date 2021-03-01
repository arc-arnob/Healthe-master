package com.covid_booking.covid.model;


import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Address {

    private String streetName;
    private String municipality;
    private String countrySubdivisionName;
    private String postalCode;
    private String country;

    @JsonProperty("address")
    public void unpackNested(Map<String,Object> address){
        //Map<String,Object> place = (Map<String,Object>)address.get("address");

        this.streetName = (String)address.get("streetName");
        this.municipality = (String)address.get("municipality");
        this.countrySubdivisionName = (String)address.get("countrySubdivisionName");
        this.postalCode = (String)address.get("postalCode");
        this.country = (String)address.get("country");


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

    

    // private JsonNode address;

    // ObjectMapper mapper = new ObjectMapper();
    // Place newJsonNode = mapper.convertValue(address, Place.class);

    // public Place getNewJsonNode() {
    //     return newJsonNode;
    // }

    // public void setNewJsonNode(Place newJsonNode) {
    //     this.newJsonNode = newJsonNode;
    // }
    
    
}
