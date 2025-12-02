package com.ride.driver_service.dto;

public class RideRequestEvent {
    private String riderName;
    private String pickup;
    private String drop;

    public RideRequestEvent() {}

    public RideRequestEvent(String riderName, String pickup, String drop) {
        this.riderName = riderName;
        this.pickup = pickup;
        this.drop = drop;
    }

    public String getRiderName() { return riderName; }
    public void setRiderName(String riderName) { this.riderName = riderName; }
    public String getPickup() { return pickup; }
    public void setPickup(String pickup) { this.pickup = pickup; }
    public String getDrop() { return drop; }
    public void setDrop(String drop) { this.drop = drop; }

    @Override
    public String toString() {
        return "RideRequestEvent{riderName='" + riderName + "', pickup='" + pickup + "', drop='" + drop + "'}";
    }
}