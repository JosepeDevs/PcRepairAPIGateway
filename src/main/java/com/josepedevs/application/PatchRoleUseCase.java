package com.josepedevs.application;

import com.josepedevs.domain.entities.AuthenticationData;
import com.josepedevs.domain.exceptions.InadequateRoleException;
import com.josepedevs.domain.exceptions.UserNotFoundException;
import com.josepedevs.domain.service.JwtTokenReaderService;
import com.josepedevs.infrastructure.output.AuthJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PatchRoleUseCase {
	
	private final AuthJpaRepository repository;
	private final JwtTokenReaderService jwtReaderService;
	
	public boolean patchRole(String jwtToken, UUID id, String role) {
		
		//check if user declared in token exists
		String username = jwtReaderService.extractUsername(jwtToken);
		Optional<AuthenticationData> authenticatedAdmin = repository.findByUsername(username); 
		AuthenticationData existingAdmin = authenticatedAdmin.orElseThrow( () ->
		new UserNotFoundException("You tried to access a user that was missing or not found in our systems.", "id") );	
		
		//check if user to be changed exists
		Optional<AuthenticationData> userToBeChanged = repository.findById(id); 
		AuthenticationData existingUserToBeChanged = userToBeChanged.orElseThrow( () ->
		new UserNotFoundException("You tried to access a user that was missing or not found in our systems.", "username") );	

		//check if the token is from an ADMIN
		if(! existingAdmin.getRole().toString().equals("ADMIN")){
			throw new InadequateRoleException("You do not have the required authority to access this resource.", "existingAdmin");
		} else {
			String rolInicial = existingUserToBeChanged.getRole();
			existingUserToBeChanged.setRole(role);
			AuthenticationData savedUser = repository.save(existingUserToBeChanged);
            return !Objects.equals(rolInicial, savedUser.getRole());
		}
	}
	
}
