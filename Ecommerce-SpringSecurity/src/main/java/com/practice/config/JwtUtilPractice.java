package com.practice.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.practice.models.User;
import com.practice.service.UserAuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtilPractice {
	
	@Value("${jwt.secret}")
	private String secretkey;
	
	@Value("${jwt.token.validity}")
	private Integer tokenValidity;
	
	@Autowired
	private UserAuthService userauth;
	
	public User getUser(final String token){
		String username = extractUsername(token);
		return userauth.loadUserByUsername(username);
	}
	
	
	public String genToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *2))
				.signWith(SignatureAlgorithm.HS256, "secret")
				.compact();
	}
	
	public boolean validateToken(final String token) {
		User user= getUser(token);
		return user.getUsername().equals(extractUsername(token)) && extractExpiration(token).after(new Date());
		
	}
	
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder().setSigningKey("secret").build().parseClaimsJws(token).getBody();
	}
	
	public Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaims(token).getExpiration();
	}


	public String extractUsername(String token) {
		// TODO Auto-generated method stub
		return extractClaims(token).getSubject();
	}


}
