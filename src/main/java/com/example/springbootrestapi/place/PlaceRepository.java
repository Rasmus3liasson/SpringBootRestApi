package com.example.springbootrestapi.place;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<PlaceEntity,Integer>{
    List<PlaceEntity> findByStatus(String status);

}
