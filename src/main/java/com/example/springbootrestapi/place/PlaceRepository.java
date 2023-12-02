package com.example.springbootrestapi.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity,Integer>{
    List<PlaceEntity> findByStatus(String status);
    List<PlaceEntity> findByUserId(String status);

}
