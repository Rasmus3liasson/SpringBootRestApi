package com.example.springbootrestapi.geomaps;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
public class GeoMaps {
    private final String url = "https://geocode.maps.co/reverse";
    private final RestTemplate restTemplate = new RestTemplate();

    public AddressInfo getLocation(double latitude, double longitude) {
        // Format data
        String apiUrl = String.format("%s?format=json&lat=%s&lon=%s", url, latitude, longitude);

        ResponseData res = restTemplate.getForObject(apiUrl, ResponseData.class);

        AddressInfo addressData = new AddressInfo(
                Double.parseDouble(res.lat()), Double.parseDouble(res.lon()),
                Arrays.stream(res.display_name().split(",")).toArray(),
                res.address()
        );


        return addressData;
    }
}
