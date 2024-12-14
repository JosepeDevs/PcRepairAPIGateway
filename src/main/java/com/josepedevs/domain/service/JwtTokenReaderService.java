package com.josepedevs.domain.service;

import com.josepedevs.domain.dto.valueobjects.RoleVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Slf4j
public class JwtTokenReaderService {
	
	private final JwtTokenIssuerService jwtTokenIssuerService;

	/**
	 * check that user details in token is equal to userDetails and that the token is not expired
	 */
	public boolean isTokenUserDataValidAndNotExpired(String jwtToken, UserDetails userDetails) {
		final String username = extractUsername(jwtToken);
		return (username.equals(userDetails.getUsername()) &&  ! isTokenExpired(jwtToken) );
	}

	/**
	 * //Function from java.util returns type T and take Claims type as parameter and returns type T
	 * @return the claim in the correspondent type
	 */
	public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsTypeResolver) {
		final Claims allClaims = extractAllClaims(jwtToken);
		return claimsTypeResolver.apply(allClaims);
	}

	/**
	 * Given a token extracts all claims, then extracts claim of type equal to getSubject (String)
     */
	public String extractUsername(String jwtToken) {
		return extractClaim(jwtToken, Claims::getSubject);
	}
	
	/**
	 * Given a token extracts all roles as a List of Role
     */
	public  Map<String,Object> extractRolesAndPermissions(String jwtToken) {
		final Claims allClaims = extractAllClaims(jwtToken);
        final var rolesRaw = (List<?>) allClaims.get("roles");

        if (rolesRaw == null || ! rolesRaw.stream().allMatch(RoleVo.class::isInstance)) {
        	// (no extra claims will be added)
        	return new HashMap<>();
        }

		List<RoleVo> roles = rolesRaw.stream()
				.map(RoleVo.class::cast)
				.toList();
		Map<String,Object> extraClaims = new HashMap<>();
		StringBuilder rolesSB = new StringBuilder();
		StringBuilder permissionsSB = new StringBuilder();
		//builds something like "User, Editor," includes a comma always at the end too
		for (RoleVo role : roles){
			rolesSB.append(role.toString());
			rolesSB.append(", ");
			permissionsSB.append(role.getPermissions());
			permissionsSB.append(", ");
		}
		//remove the comma in the last position of roles
		int lastRoleIndex = rolesSB.length() - 1;
		if (lastRoleIndex >= 0) {
			rolesSB.deleteCharAt(lastRoleIndex);
		}
		//remove the comma in the last position of permissions
		int lastPermissionIndex = permissionsSB.length() - 1;
		if (lastPermissionIndex >= 0) {
			permissionsSB.deleteCharAt(lastPermissionIndex);
		}
		extraClaims.put("Roles", rolesSB.toString());
		extraClaims.put("Permissions", permissionsSB.toString());
		return extraClaims;
    }
	
	/**
	 * Given a token, extract expiration, the type hander will make it to a Date type
     */
	private Date extractExpiration(String jwtToken) {
		return extractClaim(jwtToken, Claims::getExpiration);
	}
	
	/**
	 * Gets the expiration of the token and checks current time to return true if is still valid
	 * @return boolean : true if is valid, false if not.
	 */
	private boolean isTokenExpired(String jwtToken) {
		return extractExpiration(jwtToken).before(new Date());
	}

	/**
	 * parse a JSON Web Token (JWT) and extract all its claims
	 * @return Claims (all the payLoad/Body)
	 */
	private Claims extractAllClaims(String jwtToken) {
		SecretKey secretSigningKey = jwtTokenIssuerService.getSecretSigningKey();

		//parser receives key for decipher jwt
		JwtParser jwsParser = Jwts.parser().verifyWith(secretSigningKey).build();
		// pass token to the parser
		Jws<Claims> jwsClaims = jwsParser.parseSignedClaims(jwtToken);
		//extract claims from payload
		return jwsClaims.getPayload();
	}

}
