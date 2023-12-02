package com.example.springbootrestapi.place;

import com.example.springbootrestapi.exception.RequestValidationException;
import com.example.springbootrestapi.geomaps.AddressInfo;
import com.example.springbootrestapi.geomaps.GeoMaps;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(value = "/api/location", produces = "application/json")
public class PlaceController {

    private final PlaceService placeService;
    private final GeoMaps geoMaps;

    @Autowired
    public PlaceController(PlaceService placeService, GeoMaps geoMaps) {
        this.placeService = placeService;
        this.geoMaps = geoMaps;
    }

    @GetMapping
    public List<PlaceEntity> getAllPlaces() {
        if (placeService.getAllPlaces().isEmpty()) {
            throw new EntityNotFoundException("No places available");
        }
        return placeService.getAllPlaces();
    }


    @GetMapping("/{id}")
    public Optional<PlaceEntity> getPlaceById(@PathVariable Integer id) {
        Optional<PlaceEntity> place = placeService.getPlaceById(id);

        if (place.isPresent()) {
            return place;
        } else {
            throw new EntityNotFoundException("Place with id " + id + " does not exist");
        }
    }

    @GetMapping("/category/{categoryId}")
    public List<PlaceEntity> getPlacesRelatedToCategory(@PathVariable Integer categoryId) {
        if (placeService.getPlacesByCategory(categoryId).isEmpty()) {
            throw new IllegalArgumentException("Couldn't find place with category id of " + categoryId);
        }
        return placeService.getPlacesByCategory(categoryId);

    }

    @GetMapping("/userId")
    @PreAuthorize("isAuthenticated()")
    public List<PlaceEntity> getPlacesRelatedToUser() {
        try {
            return placeService.getPlacesRelatedToUser();
        } catch (AccessDeniedException e) {
            throw new AccessDeniedException("Access denied");
        }
    }

    @GetMapping("/area")
    public List<PlaceEntity> getPlacesInArea(@PathParam("latitude") double latitude,
                                             @PathParam("longitude") double longitude,
                                             @PathParam("radius") double radius) {
        return placeService.getPlacesInArea(latitude, longitude, radius);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePlace(@PathVariable Integer id, @Valid @RequestBody PlaceEntity updatedPlace) {
        try {
            placeService.updatePlace(id, updatedPlace);
            return ResponseEntity.status(HttpStatus.OK).body("Updated place with id " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> createPlace(@Valid @RequestBody PlaceEntity newPlace) {
        try {
            placeService.createPlace(newPlace);
            return ResponseEntity.status(HttpStatus.OK).body("Created place with id " + newPlace.getPlaceId() + " successfully");
        } catch (RequestValidationException e) {
            throw new RequestValidationException("Validation failed for the request body");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlace(@PathVariable Integer id) {
        Optional<PlaceEntity> deletedPlace = placeService.deletePlace(id);

        if (deletedPlace.isPresent()) {
            return ResponseEntity.ok("Deleted place with id " + id);
        } else {
            throw new EntityNotFoundException("Place with id " + id + " does not exist");
        }
    }

    @GetMapping("/area/geoLocation")
    public ResponseEntity<AddressInfo> getAddressInfo(@PathParam("latitude") double latitude,
                                                      @PathParam("longitude") double longitude) {
        AddressInfo adressInfo = geoMaps.getLocation(latitude, longitude);

        if (adressInfo != null) {
            return ResponseEntity.status(HttpStatus.OK).body(adressInfo);
        } else {
            throw new EntityNotFoundException("We couldn't find any address on with the coordinates");
        }
    }


}


