#!/bin/bash

echo "🚖 Complete System Test"
echo "======================"

# 1. Start Kafka
echo "1. Starting Kafka..."
docker compose up -d
sleep 10

# 2. Verify Kafka topics
echo "2. Verifying Kafka topics..."
docker exec ride-booking-system-kafka-1 kafka-topics --list --bootstrap-server localhost:9092

# 3. Start services
echo "3. Starting services..."
cd rider-service && mvn spring-boot:run > ../rider.log 2>&1 &
RIDER_PID=$!
cd ../driver-service && mvn spring-boot:run > ../driver.log 2>&1 &
DRIVER_PID=$!
cd ..

# 4. Wait for startup
echo "4. Waiting 45 seconds for services..."
sleep 45

# 5. Test API
echo "5. Testing ride request..."
RESPONSE=$(curl -s -X POST http://localhost:8081/rider/request \
  -H "Content-Type: application/json" \
  -d '{"riderName": "Sumanth", "pickup": "Bangalore", "drop": "BTM"}' \
  -w "%{http_code}")

echo "Response: $RESPONSE"

# 6. Check logs
echo "6. Recent logs:"
echo "--- Rider Service ---"
tail -10 rider.log
echo "--- Driver Service ---"
tail -10 driver.log

echo "PIDs: Rider=$RIDER_PID Driver=$DRIVER_PID"