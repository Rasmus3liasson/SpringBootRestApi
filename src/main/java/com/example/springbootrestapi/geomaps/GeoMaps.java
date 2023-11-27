package com.example.springbootrestapi.geomaps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.Arrays;


@Service
public class GeoMaps {
    private final Logger logger = LoggerFactory.getLogger(GeoMaps.class);
    private final String url = "https://geocode.maps.co/reverse";
    private final RestTemplate restTemplate = new RestTemplate();

    @Retryable(retryFor = {HttpClientErrorException.class, HttpServerErrorException.class}, maxAttempts = 3, backoff = @Backoff(delay = 300))
    public AddressInfo getLocation(double latitude, double longitude) {
        // Format data
        String apiUrl = String.format("%s?format=json&lat=%s&lon=%s", url, latitude, longitude);

        ResponseData res = restTemplate.getForObject(apiUrl, ResponseData.class);

        AddressInfo addressData = new AddressInfo(
                Double.parseDouble(res.lat()), Double.parseDouble(res.lon()),
                Arrays.stream(res.display_name().split(",")).toArray(),
                res.address()
        );
        logger.info("Location data retrieval successful!");
        return addressData;
    }
}
