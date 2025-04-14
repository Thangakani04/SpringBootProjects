package com.microservice.kafka.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.kafka.dto.Order;
import com.microservice.kafka.dto.OrderEvent;
import com.microservice.kafka.kafka.Orderproducer;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
	
	private Orderproducer orderproducer;

	public OrderController(Orderproducer orderproducer) {
		this.orderproducer = orderproducer;
	}
	
	@PostMapping("/orders")
	public String placeorder(@RequestBody Order order) {
		order.setOrderid(UUID.randomUUID().toString());
		OrderEvent orderevent = new OrderEvent();
		orderevent.setStatus("PENDING");
		orderevent.setMessage("Order status is in pending state");
		orderevent.setOrder(order);
		orderproducer.SendMessage(orderevent);
		return "Order placed successfully!!";
		
		
	}
	
	

}
