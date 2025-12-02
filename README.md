# 🚖 Simple Ride Booking Mini System

A microservices-based ride booking system built with **Spring Boot** and **Apache Kafka** for event-driven communication.

## 🏗️ Architecture

- **Rider Service** (Port 8081): Handles ride requests and listens for ride acceptance
- **Driver Service** (Port 8082): Listens for ride requests and automatically accepts them
- **Apache Kafka**: Message broker for asynchronous communication

## 🚀 Quick Start

### 1. Start Kafka
```bash
docker compose up -d
```

### 2. Create Kafka Topics
```bash
# Get Kafka container ID
docker ps

# Create topics
docker exec -it <kafka_container_id> bash
kafka-topics --create --topic ride.requested --bootstrap-server localhost:9092
kafka-topics --create --topic ride.accepted --bootstrap-server localhost:9092
```

### 3. Start Services
```bash
# Terminal 1 - Rider Service
cd rider-service
./mvnw spring-boot:run

# Terminal 2 - Driver Service  
cd driver-service
./mvnw spring-boot:run
```

## 🧪 Test the System

### Request a Ride
```bash
curl -X POST http://localhost:8081/rider/request \
  -H "Content-Type: application/json" \
  -d '{
    "riderName": "Sumanth",
    "pickup": "Bangalore",
    "drop": "BTM"
  }'
```

### Expected Flow
1. **Rider Service**: Sends ride request to Kafka topic `ride.requested`
2. **Driver Service**: Receives request and automatically accepts it
3. **Driver Service**: Sends acceptance message to `ride.accepted` topic
4. **Rider Service**: Receives and displays acceptance notification

## 📋 Resume Points

✅ **Event-Driven Microservices Architecture**  
✅ **Apache Kafka Integration**  
✅ **Spring Boot REST APIs**  
✅ **Producer-Consumer Pattern**  
✅ **Docker Containerization**  
✅ **Real-time Communication**

## 🛠️ Tech Stack

- **Spring Boot 3.2.0**
- **Apache Kafka**
- **Docker & Docker Compose**
- **Maven**
- **Lombok**

## 📁 Project Structure
```
ride-booking-system/
├── rider-service/          # Handles ride requests
├── driver-service/         # Processes ride acceptance  
├── docker-compose.yml      # Kafka setup
└── README.md
```