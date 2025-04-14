package com.springsecurity.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.DTO.LoginDTO;
import com.springsecurity.config.JWT_Util;
import com.springsecurity.service.LoginService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWT_Util jwtUtil;
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<Object> authenticateUser(@RequestBody LoginDTO logindto){
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logindto.getEmail(), logindto.getPassowrd()));
			UserDetails userdetails = loginService.loadUserByUsername(logindto.getEmail());
			String jwt = jwtUtil.generateToken(userdetails);
			Map<String, Object> response = new HashMap<>();
			response.put("jwt", jwt);
			response.put("status", HttpStatus.OK);
			return ResponseEntity.ok(response);
		}
		catch (BadCredentialsException e) {
			return new ResponseEntity<>("Invalid credentials" , HttpStatus.BAD_REQUEST);
		}
	}
	
	

}
