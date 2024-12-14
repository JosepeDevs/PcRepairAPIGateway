package com.josepedevs.application;

import com.josepedevs.domain.entities.AuthenticationData;
import com.josepedevs.domain.exceptions.InadequateRoleException;
import com.josepedevs.domain.exceptions.UserNotFoundException;
import com.josepedevs.domain.service.JwtTokenReaderService;
import com.josepedevs.infrastructure.output.AuthJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAllRegisteredUseCase {

	private final AuthJpaRepository repository;
	private final JwtTokenReaderService jwtReaderService;

	public List<AuthenticationData> getAll(String jwtToken) {
		
		String username = jwtReaderService.extractUsername(jwtToken);
		Optional<AuthenticationData> userDataAuth = repository.findByUsername(username); 
		AuthenticationData existingUser = userDataAuth.orElseThrow( () ->
		new UserNotFoundException("ha intentado cambiuar la contraseña de un usuario que no existe o el token con las credenciales no lo contenia.", "username") );	
		if(existingUser.getRole().equals("ADMIN")){
			return repository.findAll();
		} else {
			throw new InadequateRoleException("You do not have the required authority to access this resource.", "Role");
		}
	}
}
