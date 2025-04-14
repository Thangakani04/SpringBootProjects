package com.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.model.BiddingModel;
import com.springsecurity.service.BiddingService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/bidding")
public class BiddingController {
	
	@Autowired
	private BiddingService biddingService;
	
	@PostMapping("/add")
	public ResponseEntity<Object> postBidding(@RequestBody BiddingModel bidmodel){
		return biddingService.postBidding(bidmodel);
	}

}
