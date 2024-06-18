package com.josepedevs.apigateway.domain.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.josepedevs.apigateway.domain.exceptions.TokenNotValidException;
import com.josepedevs.apigateway.domain.service.JwtTokenDataExtractorService;
import com.josepedevs.apigateway.domain.service.JwtTokenValidations;

import lombok.AllArgsConstructor;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>  {

    private final RouteValidator validator;
    private final JwtTokenValidations jwtValidations;
    private final JwtTokenDataExtractorService jwtExtractor;
	private final UserDetailsService userDetailsService;

    public AuthenticationFilter(RouteValidator validator,  JwtTokenValidations jwtValidations,
    							JwtTokenDataExtractorService jwtExtractor, UserDetailsService userDetailsService) {
		super(Config.class);
		this.validator = new RouteValidator();
		this.jwtValidations = jwtValidations;
		this.jwtExtractor = jwtExtractor;
		this.userDetailsService = userDetailsService;
    }

	
    public GatewayFilter apply(Config config) {
        return ((request, chain) -> {
            if (validator.isSecured.test(request.getRequest())) {
                if (!request.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = request.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
            		String username = jwtExtractor.extractUsername(authHeader); 

        			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            		if( ! jwtValidations.isTokenUserDataValidAndNotExpired(authHeader,userDetails) ) {
            			throw new 
            			TokenNotValidException("The token could not be validated, either the userDetails or the expiry was not valid", 
            									"TokenNotValidException");
            		}
                } catch (Exception e) {
                    System.out.println("Invalid access...!");
                    throw new RuntimeException("Unauthorized access to the application");
                }
            }
            return chain.filter(request);
        });
    }

    public static class Config {
    	//this can be empty since we will not be using it
    }
}
