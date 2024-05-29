package com.josepdevs.Infra.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.josepdevs.Application.Login;
import com.josepdevs.Domain.dto.AuthenticationRequest;
import com.josepdevs.Domain.dto.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

	private final Login loginUseCase;
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> login (@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(loginUseCase.login(request));

	}
	
	
}