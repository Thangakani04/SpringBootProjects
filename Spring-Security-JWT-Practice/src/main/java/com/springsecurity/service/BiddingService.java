package com.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.springsecurity.model.BiddingModel;
import com.springsecurity.model.UserModel;
import com.springsecurity.repository.BiddingRepository;

@Service
public class BiddingService {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BiddingRepository bidRepo;
	
	public ResponseEntity<Object> postBidding(BiddingModel bidmodel){
		try {
			String email = getCurrentUseremail();
			UserModel user = userService.getuserbyEmail(email);
			if(!"BIDDER".equals(user.getRole().getRolename())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
			}
			bidRepo.save(bidmodel);
			return ResponseEntity.status(HttpStatus.CREATED).body(bidmodel);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	public  String getCurrentUseremail() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return null;
	}

}
