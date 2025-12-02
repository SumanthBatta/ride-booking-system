package com.ride.rider_service.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ride.rider_service.dto.RideRequestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RiderProducer {

    @Autowired
    private KafkaTemplate<String, String> template;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendRideRequest(RideRequestEvent event){
        try {
            String jsonEvent = objectMapper.writeValueAsString(event);
            template.send("ride.requested", jsonEvent);
            System.out.println("Ride Requested: " + event);
        } catch (Exception e) {
            System.err.println("Error sending ride request: " + e.getMessage());
        }
    }
}