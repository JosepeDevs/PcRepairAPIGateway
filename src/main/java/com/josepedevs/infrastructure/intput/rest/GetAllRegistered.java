package com.josepedevs.infrastructure.intput.rest;

import com.josepedevs.application.GetAllRegisteredUseCase;
import com.josepedevs.domain.entities.AuthenticationData;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/admin")
@RestController
@AllArgsConstructor
@Hidden //this avoids OpenAPI /Swagger to map this controller
public class GetAllRegistered {
	private final GetAllRegisteredUseCase getAllUseCase;

	@GetMapping("/getall")
	public ResponseEntity<List<AuthenticationData>> getAllRegistered (@RequestHeader("Authorization") String jwtToken){
		//"Bearer " are 7 digits, with this we get in a string the token value and replace white spaces, just in case
		jwtToken = jwtToken.substring(7).replace (" ","");

		return ResponseEntity.ok(getAllUseCase.getAll(jwtToken));
	}
}

