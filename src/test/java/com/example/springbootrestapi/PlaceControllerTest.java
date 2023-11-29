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
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.isEmptyOrNullString;


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
        placeEntity.setLastModified(Timestamp.valueOf(LocalDateTime.now()));
        placeEntity.setDescription("This is a test place");
        placeEntity.setLatitude(123.456);
        placeEntity.setLongitude(789.012);
        placeEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

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
