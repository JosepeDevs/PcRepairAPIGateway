package com.josepedevs.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.josepedevs.domain.exceptions.UserAlreadyExistsException;
import com.josepedevs.domain.dto.AuthenticationResponse;
import com.josepedevs.domain.dto.RegisterRequest;
import com.josepedevs.domain.entities.AuthenticationData;
import com.josepedevs.domain.service.JwtTokenIssuerService;
import com.josepedevs.infrastructure.output.AuthJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Register {

	private final AuthJpaRepository repository;
	
	private final PasswordEncoder passwordEncoder;

	private final JwtTokenIssuerService jwtService;
	
	public AuthenticationResponse register(RegisterRequest request) {
	
		String username = request.getUsername();
		String email = request.getEmail();
		
		if(repository.findByUsername(username).isPresent()) {
			throw new UserAlreadyExistsException("That username is not available.", "username");
		} else if(repository.findByEmail(email).isPresent()) {
			throw new UserAlreadyExistsException("That email is not available.", "email");
		} else {
			AuthenticationData userAuthData = AuthenticationData.builder()
					.email(request.getEmail())
					.username(username)
					.psswrd(passwordEncoder.encode(request.getPsswrd()))
					.role("USER")
					.build();
			repository.save(userAuthData);
			var jwtToken = jwtService.generateBasicToken(userAuthData);
			return AuthenticationResponse.builder()
					.token(jwtToken)
					.build();
		}
	}
	
	
}
