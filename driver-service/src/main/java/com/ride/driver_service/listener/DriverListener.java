package com.ride.driver_service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ride.driver_service.dto.RideRequestEvent;
import com.ride.driver_service.producer.DriverProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DriverListener {

    @Autowired
    private DriverProducer producer;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "ride.requested", groupId = "driver-service")
    public void receiveRide(String message){
        try {
            RideRequestEvent event = objectMapper.readValue(message, RideRequestEvent.class);
            System.out.println("Received Ride Request: " + event);

            producer.acceptRide("Driver Rahul accepted ride from "
                    + event.getPickup() + " to " + event.getDrop());
        } catch (Exception e) {
            System.err.println("Error processing ride request: " + e.getMessage());
        }
    }
}