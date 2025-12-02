package com.ride.rider_service.service;

import com.ride.rider_service.model.PriceQuote;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PriceService {

    public List<PriceQuote> getPriceQuotes(String pickup, String drop, Double pickupLat, Double pickupLng, Double dropLat, Double dropLng) {
        List<PriceQuote> quotes = new ArrayList<>();
        
        // Calculate distance (simple calculation for demo)
        double distance = calculateDistance(pickupLat, pickupLng, dropLat, dropLng);
        String distanceStr = String.format("%.1f km", distance);
        String duration = String.format("%d min", (int)(distance * 2.5 + 5)); // More realistic time estimate
        
        // Uber prices (India rates 2024)
        quotes.add(new PriceQuote("Uber", "UberGo", calculateUberPrice(distance, "UberGo"), duration, distanceStr, true));
        quotes.add(new PriceQuote("Uber", "UberX", calculateUberPrice(distance, "UberX"), duration, distanceStr, true));
        quotes.add(new PriceQuote("Uber", "Uber Premier", calculateUberPrice(distance, "Premier"), duration, distanceStr, true));
        quotes.add(new PriceQuote("Uber", "UberXL", calculateUberPrice(distance, "UberXL"), duration, distanceStr, true));
        quotes.add(new PriceQuote("Uber", "Uber Moto", calculateUberPrice(distance, "Moto"), duration, distanceStr, distance < 20));
        
        // Ola prices (India rates 2024)
        quotes.add(new PriceQuote("Ola", "Mini", calculateOlaPrice(distance, "Mini"), duration, distanceStr, true));
        quotes.add(new PriceQuote("Ola", "Prime Sedan", calculateOlaPrice(distance, "Prime"), duration, distanceStr, true));
        quotes.add(new PriceQuote("Ola", "Prime SUV", calculateOlaPrice(distance, "SUV"), duration, distanceStr, true));
        quotes.add(new PriceQuote("Ola", "Lux", calculateOlaPrice(distance, "Lux"), duration, distanceStr, true));
        quotes.add(new PriceQuote("Ola", "Auto", calculateOlaPrice(distance, "Auto"), duration, distanceStr, distance < 15));
        quotes.add(new PriceQuote("Ola", "Bike", calculateOlaPrice(distance, "Bike"), duration, distanceStr, distance < 25));
        
        // Rapido prices (India rates 2024)
        quotes.add(new PriceQuote("Rapido", "Bike Lite", calculateRapidoPrice(distance, "Bike"), duration, distanceStr, distance < 30));
        quotes.add(new PriceQuote("Rapido", "Auto", calculateRapidoPrice(distance, "Auto"), duration, distanceStr, distance < 20));
        quotes.add(new PriceQuote("Rapido", "Cab Economy", calculateRapidoPrice(distance, "Cab"), duration, distanceStr, true));
        
        return quotes;
    }
    
    private double calculateDistance(Double lat1, Double lng1, Double lat2, Double lng2) {
        if (lat1 == null || lng1 == null || lat2 == null || lng2 == null) {
            return 5.0; // Default distance
        }
        
        // Haversine formula for distance calculation
        double R = 6371; // Earth's radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
    
    private double calculateUberPrice(double distance, String vehicleType) {
        double baseFare, perKmRate, perMinRate = 1.5;
        
        switch (vehicleType) {
            case "UberGo":
                baseFare = 55; perKmRate = 11; break;
            case "UberX":
                baseFare = 65; perKmRate = 13; break;
            case "Premier":
                baseFare = 85; perKmRate = 18; break;
            case "UberXL":
                baseFare = 95; perKmRate = 20; break;
            case "Moto":
                baseFare = 25; perKmRate = 4.5; perMinRate = 0.8; break;
            default:
                baseFare = 55; perKmRate = 11;
        }
        
        double timeCharge = (distance * 2.5) * perMinRate; // Estimated time
        double price = baseFare + (distance * perKmRate) + timeCharge;
        
        // Add surge pricing (10-30% randomly)
        double surge = 1 + (Math.random() * 0.2);
        price *= surge;
        
        return Math.round(price);
    }
    
    private double calculateOlaPrice(double distance, String vehicleType) {
        double baseFare, perKmRate, perMinRate = 1.2;
        
        switch (vehicleType) {
            case "Mini":
                baseFare = 50; perKmRate = 10; break;
            case "Prime":
                baseFare = 70; perKmRate = 14; break;
            case "SUV":
                baseFare = 90; perKmRate = 19; break;
            case "Lux":
                baseFare = 120; perKmRate = 25; break;
            case "Auto":
                baseFare = 25; perKmRate = 8; perMinRate = 1.0; break;
            case "Bike":
                baseFare = 20; perKmRate = 4; perMinRate = 0.7; break;
            default:
                baseFare = 50; perKmRate = 10;
        }
        
        double timeCharge = (distance * 2.5) * perMinRate;
        double price = baseFare + (distance * perKmRate) + timeCharge;
        
        // Ola typically 5-15% cheaper than Uber
        price *= 0.92;
        
        return Math.round(price);
    }
    
    private double calculateRapidoPrice(double distance, String vehicleType) {
        double baseFare, perKmRate, perMinRate = 1.0;
        
        switch (vehicleType) {
            case "Bike":
                baseFare = 15; perKmRate = 3.5; perMinRate = 0.5; break;
            case "Auto":
                baseFare = 20; perKmRate = 7; perMinRate = 0.8; break;
            case "Cab":
                baseFare = 45; perKmRate = 9.5; perMinRate = 1.1; break;
            default:
                baseFare = 15; perKmRate = 3.5;
        }
        
        double timeCharge = (distance * 2.5) * perMinRate;
        double price = baseFare + (distance * perKmRate) + timeCharge;
        
        // Rapido is typically the cheapest
        price *= 0.85;
        
        return Math.round(price);
    }
}