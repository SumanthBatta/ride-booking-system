package com.ride.rider_service.service;

import com.ride.rider_service.model.RideRequest;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RideService {
    private final Map<String, RideRequest> rides = new ConcurrentHashMap<>();

    public RideRequest createRide(String passengerName, String pickup, String drop) {
        String rideId = "RIDE-" + System.currentTimeMillis();
        RideRequest ride = new RideRequest(rideId, passengerName, pickup, drop);
        rides.put(rideId, ride);
        return ride;
    }

    public List<RideRequest> getAllRides() {
        return new ArrayList<>(rides.values());
    }

    public List<RideRequest> getRidesByPassenger(String passengerName) {
        return rides.values().stream()
                .filter(ride -> ride.getPassengerName().equals(passengerName))
                .toList();
    }

    public List<RideRequest> getAvailableRides() {
        return rides.values().stream()
                .filter(ride -> "REQUESTED".equals(ride.getStatus()))
                .toList();
    }

    public RideRequest acceptRide(String rideId, String driverName) {
        if (rideId == null || rideId.trim().isEmpty()) {
            return null;
        }
        
        RideRequest ride = rides.get(rideId);
        if (ride != null && "REQUESTED".equals(ride.getStatus())) {
            ride.setStatus("ACCEPTED");
            ride.setDriverName(driverName);
            return ride;
        }
        
        // Return null if ride doesn't exist or is not in REQUESTED status
        return null;
    }

    public RideRequest getRideById(String rideId) {
        return rides.get(rideId);
    }
}