package com.ride.rider_service.service;

import com.ride.rider_service.model.User;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public UserService() {
        // Users will be created through registration
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User register(String username, String password, String role, String fullName) {
        if (users.containsKey(username)) {
            return null; // User already exists
        }
        User user = new User(username, password, role, fullName);
        users.put(username, user);
        return user;
    }
    
    public User register(String username, String password, String role, String fullName, String gender) {
        if (users.containsKey(username)) {
            return null; // User already exists
        }
        User user = new User(username, password, role, fullName, gender);
        users.put(username, user);
        return user;
    }
}