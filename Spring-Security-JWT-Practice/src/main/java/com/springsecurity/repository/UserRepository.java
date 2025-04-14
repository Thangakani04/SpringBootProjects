package com.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.springsecurity.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

	UserModel findByEmail(String username);
	
  //  UserModel findByEmail(String email);
}
