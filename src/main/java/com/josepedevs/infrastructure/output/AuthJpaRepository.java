package com.josepedevs.infrastructure.output;

import com.josepedevs.domain.entities.AuthenticationData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthJpaRepository extends JpaRepository<AuthenticationData, UUID> {

	Optional<AuthenticationData> findByUsername(String username);

	Optional<AuthenticationData> findByEmail(String email);
	
}
