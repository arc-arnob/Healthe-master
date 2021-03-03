package com.booking.booking_per_user.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.booking.booking_per_user.dao.BookingDao;
import com.booking.booking_per_user.model.Coordinates;
import com.booking.booking_per_user.model.Document;
import com.booking.booking_per_user.model.Location;
import com.booking.booking_per_user.model.LocationResponse;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private BookingDao bookingDao;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Document> getAllMarkers() {
        
        return bookingDao.findAll();
    }

    @Override
    public Document createMarker(Document document) {
        
        return bookingDao.save(document);
    }

    @Override
    public List<LocationResponse> findByLocationNear(double lat, double lon) {
        Query q =  new Query();
        Point point = new Point(lon,lat);
        //1.
        NearQuery query = NearQuery.near(point);
        query.spherical(true);
        query.inKilometers();
        query.maxDistance(100);
        query.minDistance(0);
        query.query(q);
        query.limit(100);
        GeoResults<Document> data = mongoTemplate.geoNear(query, Document.class);
        System.out.println(data.getContent()); //debug
        // System.out.println(data
        // .getContent().get(0).getDistance().getMetric().toString());
         // store name, street name, distance
        List<LocationResponse> locationResponses = new ArrayList();
        int i = 0;
        for(GeoResult<Document> dat : data){
            if(i>=10){
                break;
            }
            //System.out.println(data.iterator().getClass().getName());
            LocationResponse res = new LocationResponse();
            res.setStoreName(dat.getContent().getStoreName());
            res.setStreetName(dat.getContent().getStreetName());
            res.setDistance(dat.getDistance().getValue());

            res.setMetric(dat.getDistance().getMetric().toString());
            locationResponses.add(res);
            // System.out.println("RUNUUUUDHDDUSHSHDIHSIDHSIDHIHDS");      
            i++;  
        }
        
        return locationResponses;

        //2.
    
        // List<Document> locations = mongoTemplate.find(new Query(Criteria.where("location").nearSphere(point).minDistance(0.01).maxDistance(100)), Document.class);
        // return locations.size();
        
        
        // 3.
        // BasicQuery query2 = new BasicQuery("createIndex({location:\"2dsphere\"})")
        // BasicQuery query1 = new BasicQuery("db.document.find({location:{ $near: { $geometry: { type: 'Point',coordinates: ["+ lon+","+ lat+" ] },$maxDistance: 100, $minDistance: 10}}})");
        // List<Document> data  = mongoTemplate.find(query1, Document.class);
        // return data.size();
        //return null;

        //4.
        // Criteria criteria = Criteria.where("location").nearSphere(point).maxDistance(1);
        // Query query = new Query(criteria);
        // List<Document> locations = mongoTemplate.find(query, Document.class);
        // List<Document> locations = bookingDao.findNearLocation(lon, lat);
        // return locations.size();

        // db.document.find({location:{$near:{$geometry:{type: "Point",coordinates:[-0.04631638526916504,51.495849574119305]},$maxDistance:100, $minDistance:1}}}).count()
        

        
    }

   

  

    
    
}
