package com.josepedevs.infrastructure.output.postgresql;

import com.josepedevs.domain.entities.AuthenticationData;
import com.josepedevs.domain.repository.AuthRepository;
import com.josepedevs.infrastructure.output.AuthJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserPostgreSqlAdapter implements AuthRepository{

    private final AuthJpaRepository userJpaRepository;
	
    public UserPostgreSqlAdapter(AuthJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }
    
	//////////////Commands////////////////

	@Override
    public AuthenticationData registerUserAuthData(AuthenticationData userAuthData) {
	        userJpaRepository.save(userAuthData);
	        //after save, it should contain the UUID, in the future this will return bool, and the event will publish the UUID
	        return userAuthData;
	}

	@Override
	public Optional<AuthenticationData> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
	}

}
