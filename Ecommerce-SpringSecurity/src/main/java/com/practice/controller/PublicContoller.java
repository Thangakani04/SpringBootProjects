package com.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.config.JwtUtilPractice;
import com.practice.models.Product;
import com.practice.models.User;
import com.practice.repo.ProductRepo;


@RestController
@RequestMapping("/api/public")
public class PublicContoller {
	
	@Autowired
	private ProductRepo prodRepo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtilPractice jwtUtil;
	
	@GetMapping("product/search")
	public ResponseEntity<Object> getProduct(@RequestParam (value ="keyword" , required =false) String keyword)
	{
		if(keyword ==null || keyword.trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("keyword must be provided");
		}
		List<Product> products = prodRepo.findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(keyword, keyword);
		if(products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found for the given keyword");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody User user){
		try {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		String token = jwtUtil.genToken(user.getUsername());
		return ResponseEntity.ok(token);
		}
		catch(DisabledException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is disabled");
		}
		catch(BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authentication failed");
		}
	}

}
