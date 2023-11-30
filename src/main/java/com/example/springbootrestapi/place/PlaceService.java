package com.example.springbootrestapi.place;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlaceService {


    private final PlaceRepository placeRepository;


    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }


    private boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth instanceof JwtAuthenticationToken && auth.isAuthenticated();
    }

    private boolean isAuthenticatedWithAdminRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) auth;
            Jwt jwt = jwtAuth.getToken();

            Set<String> scopes = new HashSet<>(jwt.getClaimAsStringList("scope"));
            return scopes.contains("admin");
        } else {
            return false;
        }
    }





    public List<PlaceEntity> getAllPlaces() {
        if (isAuthenticated()) {
            return placeRepository.findAll();
        } else {
            return placeRepository.findByStatus("public");
        }
    }

    public Optional<PlaceEntity> getPlaceById(Integer id) {
        if (isAuthenticated()) {
            return placeRepository.findById(id);
        } else {
            List<PlaceEntity> publicPlaces = placeRepository.findByStatus("public");
            return publicPlaces.stream()
                    .filter(place -> place.getPlaceId() == id)
                    .findFirst();
        }
    }

    public List<PlaceEntity> getPlacesByCategory(Integer category) {
        if (isAuthenticated()) {
        return placeRepository.findAll().stream().filter(p -> p.getCategoryId() == category).toList();

        }
        else {
            return placeRepository.findAll().stream()
                    .filter(p -> p.getCategoryId() == category && "public".equals(p.getStatus()))
                    .toList();
        }
    }

    public List<PlaceEntity> getPlacesRelatedToUser() {
            String userId = getCurrentUserId();
        if (isAuthenticated()) {
            return placeRepository.findAll().stream().filter(p -> p.getUserId().toLowerCase().equals(userId)).toList();
        }
        throw new IllegalStateException("No related places");
    }

    private String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) auth;
            Jwt jwt = jwtAuth.getToken();
            return jwt.getClaimAsString("preferred_username").toLowerCase();
        } else {
            throw new IllegalStateException("Not authenticated");
        }
    }

/*    public List<PlaceEntity> getPlacesRelatedToUser()  {
        if (isAuthenticatedWithAdminRole()){
        return placeRepository.findAll().stream().filter(p -> p.getUserId().equals("admin")).toList();
        }
        throw new IllegalStateException("No related places");

    }*/

 /*   public String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return userDetails.getUsername();
            }
        }
        throw new RuntimeException("User isn't signed in");
    }*/

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

    public PlaceEntity updatePlace(Integer id, @Valid PlaceEntity updatedPlace) {
        Optional<PlaceEntity> place = placeRepository.findById(id);
        if (place.isPresent()) {
            PlaceEntity existingPlace = place.get();

            // Check if field should be updated
            if (updatedPlace.getStatus() != null) {
                existingPlace.setStatus(updatedPlace.getStatus());
            }
            existingPlace.setName(updatedPlace.getName());
            existingPlace.setCategoryId(updatedPlace.getCategoryId());
            existingPlace.setLatitude(updatedPlace.getLatitude());
            existingPlace.setLongitude(updatedPlace.getLongitude());
            existingPlace.setDescription(updatedPlace.getDescription());

            return placeRepository.save(existingPlace);
        }
        return null;
    }

    public boolean placeExists(String placeName) {
        return placeRepository.findAll().stream()
                .anyMatch(place -> place.getName().equalsIgnoreCase(placeName));
    }

    public List<PlaceEntity> createPlace(PlaceEntity newPlace) {
        if (!placeExists(newPlace.getName())) {
            placeRepository.save(newPlace);
        }
        return null;
    }

    public Optional<PlaceEntity> deletePlace(Integer id) {
        Optional<PlaceEntity> place = placeRepository.findById(id);
        place.ifPresent(placeRepository::delete);
        return place;
    }
}