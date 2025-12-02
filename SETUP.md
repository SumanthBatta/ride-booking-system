# 🚖 Simple Ride Booking System - Setup Guide

## ✅ What's Complete

1. **Docker Compose** - Kafka & Zookeeper setup ✅
2. **Kafka Topics** - `ride.requested` and `ride.accepted` created ✅
3. **Project Structure** - Both services created ✅
4. **Source Code** - All Java classes implemented ✅

## 🚀 Quick Start

### 1. Start Kafka
```bash
docker compose up -d
```

### 2. Verify Kafka Topics
```bash
docker exec ride-booking-system-kafka-1 kafka-topics --list --bootstrap-server localhost:9092
```

### 3. Start Services (Manual)
```bash
# Terminal 1 - Rider Service
cd rider-service
mvn clean compile exec:java -Dexec.mainClass="com.ride.rider_service.RiderServiceApplication"

# Terminal 2 - Driver Service  
cd driver-service
mvn clean compile exec:java -Dexec.mainClass="com.ride.driver_service.DriverServiceApplication"
```

### 4. Test the System
```bash
curl -X POST http://localhost:8081/rider/request \
  -H "Content-Type: application/json" \
  -d '{"riderName": "Sumanth", "pickup": "Bangalore", "drop": "BTM"}'
```

## 📋 Expected Flow

1. **Rider Service** receives POST request
2. **Rider Service** sends `RideRequestEvent` to `ride.requested` topic
3. **Driver Service** receives the event
4. **Driver Service** sends acceptance message to `ride.accepted` topic
5. **Rider Service** receives and displays acceptance

## 🏆 Resume Points

✅ **Event-Driven Microservices Architecture**  
✅ **Apache Kafka Integration**  
✅ **Spring Boot REST APIs**  
✅ **Producer-Consumer Pattern**  
✅ **Docker Containerization**  
✅ **Real-time Communication**

## 🛠️ Tech Stack

- **Spring Boot 3.2.0**
- **Apache Kafka 7.4.0**
- **Docker & Docker Compose**
- **Maven**
- **Lombok**

## 📁 Project Structure
```
ride-booking-system/
├── rider-service/          # Port 8081
│   ├── src/main/java/com/ride/rider_service/
│   │   ├── RiderServiceApplication.java
│   │   ├── dto/RideRequestEvent.java
│   │   ├── controller/RiderController.java
│   │   ├── producer/RiderProducer.java
│   │   └── listener/RiderListener.java
│   └── pom.xml
├── driver-service/         # Port 8082
│   ├── src/main/java/com/ride/driver_service/
│   │   ├── DriverServiceApplication.java
│   │   ├── dto/RideRequestEvent.java
│   │   ├── producer/DriverProducer.java
│   │   └── listener/DriverListener.java
│   └── pom.xml
├── docker-compose.yml      # Kafka setup
└── README.md
```

## 🎯 Project Complete!

Your Simple Ride Booking Mini System is ready for:
- **GitHub Portfolio**
- **Resume Projects**
- **Interview Discussions**
- **Further Enhancements**