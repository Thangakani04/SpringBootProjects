package com.practice.restApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.restApi.entity.Orders;

@Repository
public interface RestApiRepo extends JpaRepository<Long, Orders> {

	List<Orders> findByCustomerIdOrderByCreatedDateDesc(Long id);
	
	

}
