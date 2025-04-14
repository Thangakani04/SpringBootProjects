package com.microservice.kafka.dto;

public class Order {
	
	private String orderid;
	private String ordername;
	private int qty;
	private double price;
	
	public Order(String orderid, String ordername, int qty, double price) {
		
		this.orderid = orderid;
		this.ordername = ordername;
		this.qty = qty;
		this.price = price;
	}
	
  public Order() {
		
		
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrdername() {
		return ordername;
	}

	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [orderid=" + orderid + ", ordername=" + ordername + ", qty=" + qty + ", price=" + price + "]";
	}
	
	
	
	
	

}
