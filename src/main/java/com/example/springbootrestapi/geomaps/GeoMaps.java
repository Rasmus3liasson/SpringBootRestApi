package com.example.springbootrestapi.geomaps;

import org.springframework.web.client.RestTemplate;

public class GeoMaps {
    private final String url = "https://geocode.maps.co/reverse";
    private final RestTemplate restTemplate = new RestTemplate();

    public Address getLocation(double latitude, double longitude) {
        // Format data
        String apiUrl = String.format("%s?format=json&lat=%s&lon=%s", url, latitude, longitude);

        ResponseData res = restTemplate.getForObject(apiUrl, ResponseData.class);

        Address adressData = res.address();

        return adressData;
    }
}
