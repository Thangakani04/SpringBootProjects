package com.practice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.models.User;
import com.practice.repo.UserRepo;

@Service
public class UserAuthService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	public User loadUserById(Integer id) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
		else {
			throw new UsernameNotFoundException("User Id not found");
		}
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<User> user = userRepo.findByUsername(username);
		if(user.isPresent()) {
			return user.get();
		}
		else {
			throw new UsernameNotFoundException("User not found");
		}
	}

}
