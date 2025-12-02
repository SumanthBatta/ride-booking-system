package com.ride.driver_service.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DriverProducer {

    @Autowired
    private KafkaTemplate<String, String> template;

    public void acceptRide(String msg){
        template.send("ride.accepted", msg);
    }
}