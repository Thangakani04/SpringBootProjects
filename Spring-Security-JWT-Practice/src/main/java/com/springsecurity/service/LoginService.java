package com.springsecurity.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecurity.model.UserModel;
import com.springsecurity.repository.UserRepository;



@Service
public class LoginService implements UserDetailsService {
	
	@Autowired
	UserRepository userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = userrepo.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User name not found");
		}
		else {
			return builduserforAuthentication(user);
		}
	}

	private UserDetails builduserforAuthentication(UserModel user) {
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRolename()));
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

}
