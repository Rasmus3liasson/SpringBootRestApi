package com.example.springbootrestapi.geomaps;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

public record ResponseData(long place_id,
                           String licence,
                           String powered_by,
                           String osm_type,
                           long osm_id,
                           String lat,
                           String lon,
                           String display_name,
                           Address address,
                           String[] boundingbox) {


}
