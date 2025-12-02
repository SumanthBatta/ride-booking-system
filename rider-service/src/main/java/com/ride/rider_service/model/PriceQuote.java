package com.ride.rider_service.model;

public class PriceQuote {
    private String platform;
    private String vehicleType;
    private double price;
    private String duration;
    private String distance;
    private boolean available;

    public PriceQuote() {}

    public PriceQuote(String platform, String vehicleType, double price, String duration, String distance, boolean available) {
        this.platform = platform;
        this.vehicleType = vehicleType;
        this.price = price;
        this.duration = duration;
        this.distance = distance;
        this.available = available;
    }

    // Getters and Setters
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getDistance() { return distance; }
    public void setDistance(String distance) { this.distance = distance; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}