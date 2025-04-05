package com.example.springbootwithpostgresql.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootwithpostgresql.entity.UserEntity;
import com.example.springbootwithpostgresql.service.UserService;

@RestController
@RequestMapping("/student")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<UserEntity> getAlluser()
	{
		List<UserEntity> users= userService.getAlluser();
		return users;
		
	}

	@RequestMapping("/{rollno}")
	public UserEntity getUserbyId(@PathVariable int rollno)
	{
		UserEntity user = userService.getUserbyId(rollno);
		return user;
		
	}
	
	@PostMapping
	public String addUser(@RequestBody UserEntity user)
	{
		userService.addUser(user);
		return "User saved successfully..";
	}
	
	@PutMapping("/{rollno}")
	public String updateuser(@RequestBody UserEntity user, @PathVariable int rollno)
	{
		userService.updateUser(user, rollno);
		return "User updated successfully..";
	}
	
	@DeleteMapping("/{rollno}")
	public String deleteUser(@PathVariable int rollno)
	{
		userService.deleteuser(rollno);
		return "User deleted successfullyy..";
	}
}
