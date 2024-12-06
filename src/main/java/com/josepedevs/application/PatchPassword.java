package com.josepedevs.application;

import com.josepedevs.domain.entities.AuthenticationData;
import com.josepedevs.domain.exceptions.UserNotFoundException;
import com.josepedevs.domain.service.JwtTokenReaderService;
import com.josepedevs.infrastructure.output.AuthJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatchPassword {

	private final AuthJpaRepository repository;
	private final JwtTokenReaderService jwtReaderService;
	private final PasswordEncoder passwordEncoder;

	public boolean patchPassword(String jwtToken, String newpassword) {
		//here is being throw the error
		String username = jwtReaderService.extractUsername(jwtToken);
		Optional<AuthenticationData> userDataAuth = repository.findByUsername(username); 
		AuthenticationData existingUser = userDataAuth.orElseThrow( () ->
		new UserNotFoundException("ha intentado cambiuar la contraseña de un usuario que no existe o el token con las credenciales no lo contenia.", "username") );	
		existingUser.setPsswrd(passwordEncoder.encode(newpassword));
		
		AuthenticationData savedUser = repository.save(existingUser);
        return savedUser != existingUser;
	}
	

}
