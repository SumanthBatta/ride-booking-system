package com.ride.rider_service.controller;

import com.ride.rider_service.dto.RideRequestEvent;
import com.ride.rider_service.producer.RiderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rider")
public class RiderController {

    @Autowired
    private RiderProducer producer;

    @PostMapping("/request")
    public String requestRide(@RequestBody RideRequestEvent event){
        producer.sendRideRequest(event);
        return "Ride Request Sent!";
    }
}