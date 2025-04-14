package com.springsecurity.DTO;

public class LoginDTO {
	
	private String email;
	private String passowrd;
	
	public LoginDTO(String email, String passowrd) {
		
		this.email = email;
		this.passowrd = passowrd;
	}
	
	public LoginDTO() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassowrd() {
		return passowrd;
	}

	public void setPassowrd(String passowrd) {
		this.passowrd = passowrd;
	}
	
	
	
	

}
