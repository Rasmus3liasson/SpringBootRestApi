package com.example.springbootrestapi.geomaps;

import java.util.Arrays;

public record AddressInfo(double lat,double lon, Object[] display_name, Address address ) {
    @Override
    public String toString() {
        return "AddressInfo{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", display_name=" + Arrays.toString(display_name) +
                ", address=" + address +
                '}';
    }
}
