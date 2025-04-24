package com.practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/seller")
public class SellerController {
	
	@GetMapping("/search")
	public String Hello()
	{
		return "hello";
		
	}


}
