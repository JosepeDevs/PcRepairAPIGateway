package com.josepedevs.domain.entities;


import com.josepedevs.domain.dto.valueobjects.RoleVo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name="authentication_data")
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@Data
public class AuthenticationData implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7466891722159308882L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_user")
	@Id
	private UUID idUser;

	@Column(name = "username")
	private String username;
	
	@Column(name = "email")
	private String email;

	@Column(name = "psswrd")
	private String psswrd;
	
	@Column(name = "role")
	private String role;

	@Column(name = "psswrd_salt")
	private String psswrdSalt;

	@Column(name = "registration_token")
	private String registrationToken;
	
	@Column(name = "psswrdchange_token")
	private String psswrdChangeToken;
	
	@Column(name = "psswrdtoken_issuedate")
	private String 	psswrdTokenIssuedate;
	
	@Column(name = "active")
	private boolean	active;

	
	public AuthenticationData(UUID idUser, String username, String email, String psswrd,
							  RoleVo role, String psswrdSalt, String registrationToken,
							  String psswrdChangeToken, String 	psswrdTokenIssuedate, boolean active) {
		
		   this.idUser = idUser;
	        this.username = username;
	        this.email = email;
	        this.psswrd = psswrd;
	        this.role = role.name();
	        this.psswrdSalt = psswrdSalt;
	        this.registrationToken = registrationToken;
	        this.psswrdChangeToken = psswrdChangeToken;
	        this.psswrdTokenIssuedate = psswrdTokenIssuedate;
	        this.active = active;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of(new SimpleGrantedAuthority(role));
	}
	
	/*//This would be used if more than one role is allowed
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return role.stream()
	                .map(role -> new SimpleGrantedAuthority(role.name()))
	                .collect(Collectors.toList());
	}
	 */
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getPassword() {
		return psswrd;
	}
	
}
