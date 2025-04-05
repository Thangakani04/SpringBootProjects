package com.example.springbootwithpostgresql.service;

import java.util.List;

import com.example.springbootwithpostgresql.entity.UserEntity;

public interface UserService {
	
	List<UserEntity> getAlluser();
	
	UserEntity getUserbyId(int rollno);
	
	void addUser(UserEntity user);
	
	void updateUser(UserEntity user,int rollno);
	
	void deleteuser(int rollno);
	

}
