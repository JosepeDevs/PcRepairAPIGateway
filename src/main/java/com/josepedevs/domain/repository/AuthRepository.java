package com.josepedevs.domain.repository;

import com.josepedevs.domain.entities.AuthenticationData;

import java.util.Optional;

public interface AuthRepository {

    AuthenticationData registerUserAuthData(AuthenticationData authData);
    
    Optional<AuthenticationData> findByUsername(String username);
	
}
