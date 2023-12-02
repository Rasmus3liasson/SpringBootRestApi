package com.example.springbootrestapi;

import com.example.springbootrestapi.config.SpringSecurityConfig;
import com.example.springbootrestapi.geomaps.GeoMaps;
import com.example.springbootrestapi.mapper.ResponseMapper;
import com.example.springbootrestapi.place.PlaceController;
import com.example.springbootrestapi.place.PlaceEntity;
import com.example.springbootrestapi.place.PlaceService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.sql.Timestamp;

import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = PlaceController.class)
@Import(SpringSecurityConfig.class)
@ComponentScan(basePackages = "com.example.springbootrestapi.mapper")
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResponseMapper responseMapper;

    @MockBean
    private PlaceService placeService;

    @MockBean
    private GeoMaps geoMaps;


    @Test
    void shouldReturnPlaceWithId() throws Exception {

        int placeId = 1;
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setPlaceId(placeId);
        placeEntity.setName("Test Place");
        placeEntity.setCategoryId(1);
        placeEntity.setUserId("rasmus");
        placeEntity.setStatus("public");
        Timestamp timestamp = Timestamp.valueOf("2023-11-30 10:51:01.457");
        placeEntity.setLastModified(timestamp);
        placeEntity.setCreatedAt(timestamp);
        placeEntity.setDescription("This is a test place");
        placeEntity.setLatitude(123.456);
        placeEntity.setLongitude(789.012);

        when(placeService.getPlaceById(placeId)).thenReturn(Optional.of(placeEntity));

        mockMvc.perform(get("/api/location/{placeId}", placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(responseMapper.convertJsonToObject(placeEntity, PlaceEntity.class));

    }

    @Test
    void shouldReturnNotFound() throws Exception {
        int placeId = 2;
        when(placeService.getPlaceById(placeId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/location/{placeId}", placeId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON)).andExpect(responseMapper.containsError(
                        "Not a valid request", String.format("Place with id %d does not exist", placeId)));


    }
}
