package com.josepedevs.infrastructure.intput.rest;

import com.josepedevs.application.PatchRoleUseCase;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/admin")
@RestController
@AllArgsConstructor
@Hidden //this avoids OpenAPI /Swagger to map this controller
public class UpdateRoleController {
	private final PatchRoleUseCase patchRoleUseCase;
	
	@PatchMapping("/patchrole")
	public ResponseEntity<Boolean> patchRole (@RequestHeader("Authorization") String jwtToken, @RequestBody UpdateRoleRequest request){
		//"Bearer " are 7 digits, with this we get in a string the token value and replace white spaces, just in case
		jwtToken = jwtToken.substring(7).replace (" ","");

		boolean roleChanged = patchRoleUseCase.patchRole(jwtToken, UUID.fromString(request.getId()), request.getRole());
		if(	roleChanged ) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(true);
		}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
	}
	
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class UpdateRoleRequest {
	
	String id;
	String role;
}

