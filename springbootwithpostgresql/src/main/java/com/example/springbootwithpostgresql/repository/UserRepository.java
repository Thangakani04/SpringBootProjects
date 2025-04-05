package com.example.springbootwithpostgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springbootwithpostgresql.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

}
