#!/bin/bash

echo "🚖 Testing Simple Ride Booking System"
echo "======================================"

# Kill any existing Maven processes
pkill -f "spring-boot:run"

# Start Rider Service
echo "Starting Rider Service..."
cd rider-service
mvn spring-boot:run > rider.log 2>&1 &
RIDER_PID=$!
cd ..

# Start Driver Service  
echo "Starting Driver Service..."
cd driver-service
mvn spring-boot:run > driver.log 2>&1 &
DRIVER_PID=$!
cd ..

# Wait for services to start
echo "Waiting for services to start..."
sleep 30

# Test the API
echo "Testing ride request..."
curl -X POST http://localhost:8081/rider/request \
  -H "Content-Type: application/json" \
  -d '{"riderName": "Sumanth", "pickup": "Bangalore", "drop": "BTM"}' \
  -w "\nHTTP Status: %{http_code}\n"

echo ""
echo "Check logs:"
echo "Rider Service: rider-service/rider.log"
echo "Driver Service: driver-service/driver.log"

echo ""
echo "To stop services:"
echo "kill $RIDER_PID $DRIVER_PID"