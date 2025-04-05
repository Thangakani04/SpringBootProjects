package com.example.springbootwithpostgresql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student", schema = "public")
public class UserEntity {
	
	@Id
	private int rollno;
	
	@Column
	private String studentname;
	
	@Column
	private String department;
	
	

	public UserEntity(int rollno, String studentname, String department) {
		super();
		this.rollno = rollno;
		this.studentname = studentname;
		this.department = department;
	}
	
	public UserEntity() {
		
	}

	public int getRollno() {
		return rollno;
	}

	public void setRollno(int rollno) {
		this.rollno = rollno;
	}

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	

}
