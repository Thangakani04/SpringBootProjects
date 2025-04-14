package com.ecommerce.dto;

public class OrderResponseDTO {
	
	private Long orderId;
	private Long productId;
	private int quantity;
	private double totalPrice;
	
	//product details
	
	private String productName;
	private double productPrice;
	
	
	
	
	public OrderResponseDTO(Long orderId, Long productId, int quantity, double totalPrice, String productName,
			double productPrice) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.productName = productName;
		this.productPrice = productPrice;
	}
	
	
	public OrderResponseDTO() {
		
	}


	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long id) {
		this.orderId = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	
	

}
