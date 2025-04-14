package com.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springsecurity.model.UserModel;
import com.springsecurity.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public UserModel getuserbyEmail(String email) {
		return userRepo.findByEmail(email);
		
	}

}
