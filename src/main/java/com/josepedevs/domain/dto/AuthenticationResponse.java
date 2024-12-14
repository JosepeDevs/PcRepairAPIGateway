package com.josepedevs.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class AuthenticationResponse {

	private final String token;
}
