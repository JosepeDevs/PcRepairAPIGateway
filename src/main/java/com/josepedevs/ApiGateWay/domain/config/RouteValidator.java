package com.josepedevs.apigateway.domain.config;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/login",
            "/eureka"
    );

    /**
     * checks if the uri contains one of the endpoints/routes listed in class RouteValidator
     */
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
	
}