package com.fresco.ecommerce.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fresco.ecommerce.service.UserAuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Autowired
	UserAuthService userDetails;

	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	public String generateToken(String username) {
 		Map<String, Object> claims = new HashMap<>(); 
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
				.signWith(SignatureAlgorithm.HS256, "secret").compact();
		 
	}

	public boolean validateToken(final String token) {
		return getExpiration(token).after(new Date());
	}
	
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
	}

	public Date getExpiration(final String token) {
		return getClaims(token).getExpiration();
	}
	
	
}
