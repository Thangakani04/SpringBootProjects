package com.practice.restApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.restApi.entity.Orders;
import com.practice.restApi.repository.RestApiRepo;

@Service
public class RestApiService {
	
	private RestApiRepo restApiRepo;
	
	@Autowired
	public RestApiService(RestApiRepo restApiRepo) {
		this.restApiRepo=restApiRepo;
	}

	public List<Orders> getLastNorders(Long id, Integer limit) {
		List<Orders> list = restApiRepo.findByCustomerIdOrderByCreatedDateDesc(id);
		return list.stream().limit(limit).toList();
	}

}
