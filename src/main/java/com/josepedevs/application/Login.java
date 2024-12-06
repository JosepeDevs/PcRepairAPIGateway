package com.josepedevs.application;

import com.josepedevs.domain.dto.AuthenticationRequest;
import com.josepedevs.domain.dto.AuthenticationResponse;
import com.josepedevs.domain.service.JwtTokenIssuerService;
import com.josepedevs.infrastructure.output.AuthJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Login {

	private final AuthenticationManager authenticationManager;
	private final AuthJpaRepository repository;
	private final JwtTokenIssuerService jwtService;

	public AuthenticationResponse login(AuthenticationRequest request) {
	    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPsswrd()));
	    var userDataAuth = repository.findByUsername(request.getUsername()).orElseThrow(); // throws NoSuchElementException

	    Map<String, Object> extraClaims = new HashMap<>();
	    extraClaims.put("authorities", userDataAuth.getAuthorities());

	    var jwtToken = jwtService.generateToken(extraClaims, userDataAuth);
	    return AuthenticationResponse.builder()
	           .token(jwtToken)
	           .build();
	}
}
