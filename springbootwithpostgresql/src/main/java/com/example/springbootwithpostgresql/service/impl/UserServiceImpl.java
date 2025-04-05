package com.example.springbootwithpostgresql.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springbootwithpostgresql.entity.UserEntity;
import com.example.springbootwithpostgresql.repository.UserRepository;
import com.example.springbootwithpostgresql.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<UserEntity> getAlluser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public UserEntity getUserbyId(int rollno) {
		// TODO Auto-generated method stub
		Optional<UserEntity> optuser= userRepository.findById(rollno);
		
		if(optuser.isPresent())
			return optuser.get();
		else
			throw new RuntimeException("User not found");
	}

	@Override
	public void addUser(UserEntity user) {
		// TODO Auto-generated method stub
		UserEntity userdetail = userRepository.save(user);
		System.out.println("User saved to db with user id" + userdetail.getRollno());
		
	}

	@Override
	public void updateUser(UserEntity user, int rollno) {
		// TODO Auto-generated method stub
		Optional<UserEntity> optuserdetails = userRepository.findById(rollno);
		if(optuserdetails.isPresent())
		{
			UserEntity opt= optuserdetails.get();
			if(opt.getStudentname() !=null || opt.getStudentname().isEmpty())
			{
				opt.setStudentname(user.getStudentname());
				System.out.println("Printing student name : " + user.getStudentname());
			System.out.println("UserServiceImpl.updateUser()");			}
			if(opt.getDepartment() !=null || opt.getDepartment().isEmpty())
			{
				opt.setDepartment(user.getDepartment());
			}
			userRepository.save(opt);
		}
		else {
			throw new RuntimeException("User not found");
		}
		
	}

	@Override
	public void deleteuser(int rollno) {
		// TODO Auto-generated method stub
		Optional<UserEntity> optuser = userRepository.findById(rollno);
		if(optuser.isPresent())
		{
			userRepository.deleteById(rollno);
		}
		else {
			throw new RuntimeException("User not found");
		}
		
	}

}
