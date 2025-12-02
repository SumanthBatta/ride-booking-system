package com.ride.rider_service.controller;

import com.ride.rider_service.dto.RideRequestEvent;
import com.ride.rider_service.model.PriceQuote;
import com.ride.rider_service.model.RideRequest;
import com.ride.rider_service.model.User;
import com.ride.rider_service.producer.RiderProducer;
import com.ride.rider_service.service.PriceService;
import com.ride.rider_service.service.RideService;
import com.ride.rider_service.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @Autowired
    private RiderProducer producer;
    
    @Autowired
    private RideService rideService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PriceService priceService;

    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            return "DRIVER".equals(user.getRole()) ? "redirect:/driver" : "redirect:/passenger";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "DRIVER".equals(user.getRole()) ? "redirect:/driver" : "redirect:/passenger";
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, 
                          @RequestParam String role, @RequestParam String fullName, 
                          @RequestParam String gender, Model model) {
        User user = userService.register(username, password, role, fullName, gender);
        if (user != null) {
            model.addAttribute("success", "Registration successful! Please login.");
            return "login";
        }
        model.addAttribute("error", "Username already exists");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/passenger")
    public String passenger(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"PASSENGER".equals(user.getRole())) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        model.addAttribute("rideRequest", new RideRequestEvent());
        return "passenger";
    }

    @PostMapping("/book-ride")
    public String bookRide(@ModelAttribute RideRequestEvent event, 
                          @RequestParam(required = false) Double pickupLat,
                          @RequestParam(required = false) Double pickupLng,
                          @RequestParam(required = false) Double dropLat,
                          @RequestParam(required = false) Double dropLng,
                          HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";
        
        RideRequest ride = rideService.createRide(user.getFullName(), event.getPickup(), event.getDrop());
        ride.setPickupLat(pickupLat);
        ride.setPickupLng(pickupLng);
        ride.setDropLat(dropLat);
        ride.setDropLng(dropLng);
        
        event.setRiderName(user.getFullName());
        producer.sendRideRequest(event);
        model.addAttribute("ride", ride);
        return "success";
    }

    @GetMapping("/driver")
    public String driver(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"DRIVER".equals(user.getRole())) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        model.addAttribute("availableRides", rideService.getAvailableRides());
        return "driver";
    }

    @PostMapping("/accept-ride")
    public String acceptRide(@RequestParam String rideId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";
        
        try {
            RideRequest ride = rideService.acceptRide(rideId, user.getFullName());
            if (ride == null) {
                model.addAttribute("error", "Ride not found or already accepted by another driver");
                model.addAttribute("user", user);
                model.addAttribute("availableRides", rideService.getAvailableRides());
                return "driver";
            }
            
            model.addAttribute("ride", ride);
            model.addAttribute("user", user);
            model.addAttribute("success", "Ride accepted successfully! Contact the passenger to coordinate pickup.");
            return "accepted";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to accept ride. Please try again.");
            model.addAttribute("user", user);
            model.addAttribute("availableRides", rideService.getAvailableRides());
            return "driver";
        }
    }

    @GetMapping("/my-rides")
    public String myRides(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";
        
        model.addAttribute("rides", rideService.getRidesByPassenger(user.getFullName()));
        model.addAttribute("user", user);
        return "my-rides";
    }
    
    @GetMapping("/price-comparison")
    public String priceComparison(@RequestParam String pickup, @RequestParam String drop,
                                 @RequestParam(required = false) Double pickupLat,
                                 @RequestParam(required = false) Double pickupLng,
                                 @RequestParam(required = false) Double dropLat,
                                 @RequestParam(required = false) Double dropLng,
                                 HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";
        
        List<PriceQuote> quotes = priceService.getPriceQuotes(pickup, drop, pickupLat, pickupLng, dropLat, dropLng);
        
        model.addAttribute("user", user);
        model.addAttribute("pickup", pickup);
        model.addAttribute("drop", drop);
        model.addAttribute("quotes", quotes);
        
        return "price-comparison";
    }
    
    @PostMapping("/book-platform-ride")
    public String bookPlatformRide(@RequestParam String pickup, @RequestParam String drop,
                                  @RequestParam String platform, @RequestParam String vehicleType,
                                  @RequestParam String price, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";
        
        RideRequest ride = rideService.createRide(user.getFullName(), pickup, drop);
        ride.setPlatform(platform);
        ride.setVehicleType(vehicleType);
        ride.setPrice(Double.parseDouble(price));
        
        model.addAttribute("ride", ride);
        model.addAttribute("user", user);
        model.addAttribute("platform", platform);
        model.addAttribute("vehicleType", vehicleType);
        model.addAttribute("price", price);
        
        return "platform-booking-success";
    }
    
    @GetMapping("/api/price-quotes")
    @ResponseBody
    public List<PriceQuote> getPriceQuotesApi(@RequestParam String pickup, @RequestParam String drop,
                                             @RequestParam(required = false) Double pickupLat,
                                             @RequestParam(required = false) Double pickupLng,
                                             @RequestParam(required = false) Double dropLat,
                                             @RequestParam(required = false) Double dropLng) {
        return priceService.getPriceQuotes(pickup, drop, pickupLat, pickupLng, dropLat, dropLng);
    }
    
    @GetMapping("/platform-selection")
    public String platformSelection(@RequestParam String pickup, @RequestParam String drop,
                                   @RequestParam(required = false) Double pickupLat,
                                   @RequestParam(required = false) Double pickupLng,
                                   @RequestParam(required = false) Double dropLat,
                                   @RequestParam(required = false) Double dropLng,
                                   HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";
        
        model.addAttribute("user", user);
        model.addAttribute("pickup", pickup);
        model.addAttribute("drop", drop);
        
        return "platform-selection";
    }
    
    @GetMapping("/vehicle-selection")
    public String vehicleSelection(@RequestParam String pickup, @RequestParam String drop,
                                  @RequestParam String platform,
                                  @RequestParam(required = false) Double pickupLat,
                                  @RequestParam(required = false) Double pickupLng,
                                  @RequestParam(required = false) Double dropLat,
                                  @RequestParam(required = false) Double dropLng,
                                  HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";
        
        List<PriceQuote> quotes = priceService.getPriceQuotes(pickup, drop, pickupLat, pickupLng, dropLat, dropLng);
        
        model.addAttribute("user", user);
        model.addAttribute("pickup", pickup);
        model.addAttribute("drop", drop);
        model.addAttribute("platform", platform);
        model.addAttribute("quotes", quotes);
        
        return "vehicle-selection";
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(Exception e, HttpSession session) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "Something went wrong. Please try again.");
        mav.addObject("message", e.getMessage());
        
        User user = (User) session.getAttribute("user");
        if (user != null) {
            mav.addObject("user", user);
            mav.addObject("backUrl", "DRIVER".equals(user.getRole()) ? "/driver" : "/passenger");
        } else {
            mav.addObject("backUrl", "/");
        }
        
        return mav;
    }
}