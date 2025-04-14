package com.microservice.kafka.dto;

public class OrderEvent {
	
	//this class we are going to use to transfer the data between producer and consumer using apache kafka
	//instance variables 
	private String message;
	private String status;
	private Order order;
	
	public OrderEvent(String message, String status, Order order) {
		
		this.message = message;
		this.status = status;
		this.order = order;
	}
	
	public OrderEvent() {
		
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderEvent [message=" + message + ", status=" + status + ", order=" + order + "]";
	}
	
	
	
	

}
