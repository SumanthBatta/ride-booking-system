#!/bin/bash

echo "🚀 Starting Ride Booking Services"
echo "================================="

# Kill existing processes
pkill -f "spring-boot:run"

# Start Kafka if not running
echo "1. Checking Kafka..."
docker compose up -d

# Wait for Kafka
sleep 10

# Start Rider Service
echo "2. Starting Rider Service on port 8081..."
cd rider-service
mvn clean spring-boot:run &
RIDER_PID=$!
cd ..

# Start Driver Service
echo "3. Starting Driver Service on port 8082..."
cd driver-service  
mvn clean spring-boot:run &
DRIVER_PID=$!
cd ..

echo "4. Waiting for services to start..."
sleep 45

echo "5. Checking if services are running..."
curl -s http://localhost:8081/actuator/health || echo "Rider service not ready"
curl -s http://localhost:8082/actuator/health || echo "Driver service not ready"

echo ""
echo "✅ Services started!"
echo "Rider PID: $RIDER_PID"
echo "Driver PID: $DRIVER_PID"
echo ""
echo "Test with: curl -X POST http://localhost:8081/rider/request -H 'Content-Type: application/json' -d '{\"riderName\":\"Test\",\"pickup\":\"A\",\"drop\":\"B\"}'"