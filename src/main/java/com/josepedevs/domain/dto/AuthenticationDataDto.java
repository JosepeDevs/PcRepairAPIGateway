package com.josepedevs.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class AuthenticationDataDto {
	
	private final String username;
    private final String email;
    private final String psswrd;
    private final String psswrdSalt;
    private final String registrationToken;
    private final String psswrdChangeToken;
    private final String psswrdChangeIssueDate;
    private final String loginToken;
    private boolean active;
    
}
