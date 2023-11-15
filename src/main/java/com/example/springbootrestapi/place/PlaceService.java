package com.example.springbootrestapi.place;

import org.springframework.beans.factory.annotation.Autowired;
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


}