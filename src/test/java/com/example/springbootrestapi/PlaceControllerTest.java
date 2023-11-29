package com.example.springbootrestapi;

import com.example.springbootrestapi.config.SpringSecurityConfig;
import com.example.springbootrestapi.geomaps.GeoMaps;
import com.example.springbootrestapi.place.PlaceController;
import com.example.springbootrestapi.place.PlaceEntity;
import com.example.springbootrestapi.place.PlaceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PlaceController.class)
@Import(SpringSecurityConfig.class)
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        placeEntity.setDescription("Test place");
        placeEntity.setLatitude(123.456);
        placeEntity.setLongitude(789.012);

        when(placeService.getPlaceById(placeId)).thenReturn(Optional.of(placeEntity));

        MvcResult result = mockMvc.perform(get("/api/location/{id}", placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String resBody = result.getResponse().getContentAsString();
        System.out.println("Body: " + resBody);


    }
}
