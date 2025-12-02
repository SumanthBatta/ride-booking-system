# 📮 Postman Testing Guide

## 🚀 Quick Setup

### 1. Import Collection
- Open Postman
- Click **Import** → **Upload Files**
- Select `Ride-Booking-System.postman_collection.json`

### 2. Start Services
```bash
# Start Kafka
docker compose up -d

# Start Rider Service (Terminal 1)
cd rider-service
mvn spring-boot:run

# Start Driver Service (Terminal 2)  
cd driver-service
mvn spring-boot:run
```

## 🧪 Test Cases

### Test 1: Basic Ride Request
**Method:** POST  
**URL:** `http://localhost:8081/rider/request`  
**Headers:** `Content-Type: application/json`  
**Body:**
```json
{
  "riderName": "Sumanth",
  "pickup": "Bangalore", 
  "drop": "BTM"
}
```
**Expected:** `200 OK` with "Ride Request Sent!"

### Test 2: Different Locations
**Body:**
```json
{
  "riderName": "John",
  "pickup": "Airport",
  "drop": "City Center"
}
```

### Test 3: Multiple Requests
Send 3-4 requests with different data to test event flow.

## 📊 Expected Results

### ✅ Success Response
```
Status: 200 OK
Body: "Ride Request Sent!"
```

### 📝 Console Logs

**Rider Service:**
```
Ride Requested: RideRequestEvent(riderName=Sumanth, pickup=Bangalore, drop=BTM)
Driver accepted: Driver Rahul accepted ride from Bangalore to BTM
```

**Driver Service:**
```
Received Ride Request: RideRequestEvent(riderName=Sumanth, pickup=Bangalore, drop=BTM)
```

## 🔍 Troubleshooting

| Issue | Solution |
|-------|----------|
| Connection refused | Check if services are running on ports 8081/8082 |
| No response | Wait 30-60 seconds for services to fully start |
| Kafka errors | Verify `docker compose up -d` is running |

## 🎯 Testing Checklist

- [ ] Kafka containers running
- [ ] Both services started
- [ ] POST request returns 200
- [ ] Rider service logs show "Ride Requested"
- [ ] Driver service logs show "Received Ride Request"
- [ ] Rider service logs show "Driver accepted"