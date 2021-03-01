package com.booking.booking_per_user.dao;

import java.util.List;

import com.booking.booking_per_user.model.Document;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookingDao extends MongoRepository<Document, Integer>{
    
    @Query("db.document.find({location:{$near:{$geometry:{type: 'Point',coordinates:[?0,?1]},$maxDistance:100, $minDistance:1}}})")
    public List<Document> findNearLocation(double lon, double lat);
// db.document.find({location:{$near:{$geometry:{type: "Point",coordinates:[-0.04631638526916504,51.495849574119305]},$maxDistance:100, $minDistance:1}}}).count()

}
