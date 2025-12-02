package com.ride.driver_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverController {

    @GetMapping("/driver/status")
    public String getStatus() {
        return "Driver Service is running on port 8082";
    }

    @PostMapping("/driver/accept")
    public String acceptRide(@RequestBody AcceptRideRequest request) {
        return "Driver " + request.getDriverName() + " accepted ride: " + request.getRideId();
    }

    public static class AcceptRideRequest {
        private String driverName;
        private String rideId;

        public String getDriverName() { return driverName; }
        public void setDriverName(String driverName) { this.driverName = driverName; }
        public String getRideId() { return rideId; }
        public void setRideId(String rideId) { this.rideId = rideId; }
    }
}