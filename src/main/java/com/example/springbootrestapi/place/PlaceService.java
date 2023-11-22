package com.example.springbootrestapi.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;


    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<PlaceEntity> getAllPlaces() {
        return placeRepository.findAll();
    }

    public Optional<PlaceEntity> getPlaceById(Integer id) {
        return placeRepository.findById(id);
    }

    public List<PlaceEntity> getPlacesByCategory(Integer category) {
        return placeRepository.findAll().stream().filter(p -> p.getCategoryId() == category).toList();
    }

    public List<PlaceEntity> getPlacesRelatedToUser() {
        String userId = getCurrentUser();
        return placeRepository.findAll().stream().filter(p -> p.getUserId().equals(userId)).toList();
    }

    public String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return userDetails.getUsername();
            }
        }
        throw new RuntimeException("User isn't signed in");
    }

    public List<PlaceEntity> getPlacesInArea(double latitude, double longitude, double radius) {
        double minLat = latitude - radius;
        double maxLat = latitude + radius;
        double minLong = longitude - radius;
        double maxLong = longitude + radius;

        return placeRepository.findAll().stream()
                .filter(p ->
                        p.getLatitude() >= minLat &&
                                p.getLatitude() <= maxLat &&
                                p.getLongitude() >= minLong &&
                                p.getLongitude() <= maxLong)
                .toList();
    }
}