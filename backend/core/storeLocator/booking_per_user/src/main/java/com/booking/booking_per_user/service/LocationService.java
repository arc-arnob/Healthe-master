package com.booking.booking_per_user.service;

import java.util.List;

import com.booking.booking_per_user.model.Document;
import com.booking.booking_per_user.model.LocationResponse;




public interface LocationService {
    public List<Document> getAllMarkers(); //returns all markers

    public Document createMarker(Document document); // Create Coordinate

    public List<LocationResponse> findByLocationNear(double lon, double lat); // find geonear points
}
