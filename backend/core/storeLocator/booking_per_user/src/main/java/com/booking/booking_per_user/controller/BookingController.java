package com.booking.booking_per_user.controller;

import java.util.List;

import com.booking.booking_per_user.model.Coordinates;
import com.booking.booking_per_user.model.Document;
import com.booking.booking_per_user.model.Location;
import com.booking.booking_per_user.model.LocationResponse;
import com.booking.booking_per_user.service.LocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {
    
    @Autowired
    private LocationService locationService;

    @GetMapping("/allmarkers")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Document> getAllMarkers(){

        return this.locationService.getAllMarkers();
    }

    @PostMapping(value="/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public Document createMarker(@RequestBody Document document) {
        return locationService.createMarker(document);
    }
    // for patient to find nearby stores.
    @GetMapping(value="/near/{latitude:.+},{longitude:.+}")
    public List<LocationResponse> getNearByCount(@PathVariable String latitude,@PathVariable String longitude){
        System.out.println(latitude);
        System.out.println(longitude);
        return locationService.findByLocationNear(Double.parseDouble(longitude), Double.parseDouble(latitude));
    } 
    
}
