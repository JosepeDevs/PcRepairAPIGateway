package com.josepedevs.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class AuthenticationRequest {

	private final String username;
	private final String psswrd;
}
