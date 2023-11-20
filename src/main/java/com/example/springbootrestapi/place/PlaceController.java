package com.example.springbootrestapi.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/location", produces = "application/json")
public class PlaceController {

    private PlaceService placeService;

    @Autowired
    public void PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public List<PlaceEntity> getAllPlaces() {
        return placeService.getAllPlaces();
    }


    @GetMapping("/{id}")
    public Optional<PlaceEntity> getPlaceById(@PathVariable Integer id) {
        Optional<PlaceEntity> place = placeService.getPlaceById(id);

        if (place.isPresent()) {
            return place;
        } else {
            throw new IllegalArgumentException("Place with id " + id + " does not exist");
        }
    }

    @GetMapping("/category/{categoryId}")
    public List<PlaceEntity> getPlacesRelatedToCategory(@PathVariable Integer categoryId) {
        return placeService.getPlacesByCategory(categoryId);

    }
    @GetMapping("/userId")
    @PreAuthorize("isAuthenticated()")
    public List<PlaceEntity> getPlacesRelatedToUser() {
        return placeService.getPlacesRelatedToUser();

    }
}


