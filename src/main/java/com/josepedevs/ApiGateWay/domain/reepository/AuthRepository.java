package com.josepedevs.apigateway.domain.reepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.josepedevs.apigateway.domain.entities.AuthenticationData;

public interface AuthRepository {
	
	
    public AuthenticationData registerUserAuthData(AuthenticationData authData, String jwtToken);
    
	public boolean login(AuthenticationData authData, String jwtToken);
    
    public Optional<AuthenticationData> findByUsername(String username);
    
	public boolean invalidateToken(AuthenticationData authData);
    
	public boolean patchPassword(AuthenticationData authData, String digestedPsswrd);
	
	public boolean patchRole(AuthenticationData authData, String role);
	
	public List<AuthenticationData> getAll();

	
}
