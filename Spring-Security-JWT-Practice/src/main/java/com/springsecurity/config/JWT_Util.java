package com.springsecurity.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWT_Util {
	
	public static final long JWT_TOKEN_VALIDITY = 5*60*60;
	
	private final String secretKey = "randomkey123";
	
	public String getUsernamefromToken(String token) {
		return extractClaims(token).getSubject();
		
	}
	
	public Date getExpirationdatefromtoken(String token) {
		return extractClaims(token).getExpiration();
	}
	
	public Claims extractClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
	public String generateToken(UserDetails userdetails) {
		Map<String,Object> claims = new HashMap<>();
		claims.put("roles", userdetails.getAuthorities());
		return dogenToken(claims,userdetails.getUsername());
 	}

	public String dogenToken(Map<String, Object> claims, String username) {
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS256 , secretKey)
				.compact();
	}
	
	public  boolean validToken(String token, UserDetails userdetails) {
		final String username = getUsernamefromToken(token);
		return username.equals(userdetails.getUsername()) && !isTokenExpired(token);
		
	}

	public boolean isTokenExpired(String token) {
		final Date  expiration = getExpirationdatefromtoken(token);
		return expiration.before(new Date());
	}

}
