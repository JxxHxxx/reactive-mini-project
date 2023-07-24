package com.jxx.reactive.place.functional_endpoint_based;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration("placeRouterV1")
public class PlaceRouter {

    @Bean
    public RouterFunction routePlace(PlaceHandler handler) {
        return route()
                .POST("/v2/places", handler::createPlace)
                .PATCH("/v2/places/{place-id}", handler::updatePlace)
                .GET("/v2/places/{place-id}", handler::findPlace)
                .GET("/v2/places", handler::findPlaces)
                .build();

    }
}
