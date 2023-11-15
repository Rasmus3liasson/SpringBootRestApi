package com.example.springbootrestapi.place;

import com.example.springbootrestapi.place.PlaceEntity;
import com.example.springbootrestapi.place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/location")
public class LocationController {

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
    }


