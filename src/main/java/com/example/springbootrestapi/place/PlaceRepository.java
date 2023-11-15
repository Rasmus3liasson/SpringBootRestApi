package com.example.springbootrestapi.place;

import org.springframework.data.jpa.repository.JpaRepository;
public interface PlaceRepository extends JpaRepository<PlaceEntity,Integer>{
}
