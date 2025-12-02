package com.ride.rider_service.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RiderListener {

    @KafkaListener(topics = "ride.accepted", groupId = "rider-service")
    public void receiveRideAccepted(String message) {
        System.out.println("Rider notified: " + message);
    }
}