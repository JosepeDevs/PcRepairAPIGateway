package com.josepedevs.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RegisterRequest {

	private final String username;
	private final String email;
	private final String psswrd;
	
}
