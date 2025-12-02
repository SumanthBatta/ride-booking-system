package com.ride.rider_service.model;

import java.time.LocalDateTime;

public class RideRequest {
    private String rideId;
    private String passengerName;
    private String pickup;
    private String drop;
    private Double pickupLat;
    private Double pickupLng;
    private Double dropLat;
    private Double dropLng;
    private Double currentLat;
    private Double currentLng;
    private String status = "REQUESTED";
    private String driverName;
    private String platform;
    private String vehicleType;
    private Double price;
    private LocalDateTime createdAt = LocalDateTime.now();

    public RideRequest() {}

    public RideRequest(String rideId, String passengerName, String pickup, String drop) {
        this.rideId = rideId;
        this.passengerName = passengerName;
        this.pickup = pickup;
        this.drop = drop;
    }

    // Getters and Setters
    public String getRideId() { return rideId; }
    public void setRideId(String rideId) { this.rideId = rideId; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public String getPickup() { return pickup; }
    public void setPickup(String pickup) { this.pickup = pickup; }
    public String getDrop() { return drop; }
    public void setDrop(String drop) { this.drop = drop; }
    public Double getPickupLat() { return pickupLat; }
    public void setPickupLat(Double pickupLat) { this.pickupLat = pickupLat; }
    public Double getPickupLng() { return pickupLng; }
    public void setPickupLng(Double pickupLng) { this.pickupLng = pickupLng; }
    public Double getDropLat() { return dropLat; }
    public void setDropLat(Double dropLat) { this.dropLat = dropLat; }
    public Double getDropLng() { return dropLng; }
    public void setDropLng(Double dropLng) { this.dropLng = dropLng; }
    public Double getCurrentLat() { return currentLat; }
    public void setCurrentLat(Double currentLat) { this.currentLat = currentLat; }
    public Double getCurrentLng() { return currentLng; }
    public void setCurrentLng(Double currentLng) { this.currentLng = currentLng; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}