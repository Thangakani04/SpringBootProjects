package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.dto.OrderResponseDTO;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entity.Order;
import com.ecommerce.fiegn.ProductClient;
import com.ecommerce.repository.OrderRepository;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductClient productClient;
	
	//create a method to place order
	/*
	 * @PostMapping("/placeorder") public Mono<ResponseEntity<OrderResponseDTO>>
	 * placeorder(@RequestBody Order order){
	 * 
	 * //Fetch product details from product service by calling webclient method
	 * return webClientBuilder.build().get().uri("http://localhost:8081/products/" +
	 * order.getProductId()).retrieve()
	 * .bodyToMono(ProductDTO.class).map(productDTO->{ OrderResponseDTO responseDTO
	 * = new OrderResponseDTO(); //responseDTO.setOrderId(order.getId());
	 * responseDTO.setProductId(order.getProductId());
	 * responseDTO.setQuantity(order.getQuantity());
	 * 
	 * //set product details responseDTO.setProductName(productDTO.getName());
	 * responseDTO.setProductPrice(productDTO.getPrice()) ; // we dont know the
	 * prodcut details so we nee dto call the prodcut dto and get
	 * responseDTO.setTotalPrice(order.getQuantity() * productDTO.getPrice());
	 * //save order details orderRepository.save(order);
	 * responseDTO.setOrderId(order.getId()); //orderid will be created
	 * automatically return ResponseEntity.ok(responseDTO); //give ctrl+shift+O ->
	 * when u give this mono will be imported
	 * 
	 * 
	 * }); //here we need to give the path which should be contact from product dto
	 * //8081 is productservice port number
	 * 
	 * }
	 */
	
	 @PostMapping("/placeorder")
	    public Mono<ResponseEntity<OrderResponseDTO>> placeOrder(@RequestBody Order order) {
	        return Mono.fromCallable(() -> {
	            // Fetch product details using Feign Client
	            ProductDTO productDTO = productClient.getProductById(order.getProductId());

	            // Map the product details into OrderResponseDTO
	            OrderResponseDTO responseDTO = new OrderResponseDTO();
	            responseDTO.setProductId(order.getProductId());
	            responseDTO.setQuantity(order.getQuantity());

	            // Set product details
	            responseDTO.setProductName(productDTO.getName());
	            responseDTO.setProductPrice(productDTO.getPrice());
	            responseDTO.setTotalPrice(order.getQuantity() * productDTO.getPrice());

	            // Save order details
	            orderRepository.save(order);
	            responseDTO.setOrderId(order.getId());  // Order ID will be created automatically

	            // Return the response
	            return ResponseEntity.ok(responseDTO);
	        });
	    }
	
	
	
	
	
	//get all orders
	@GetMapping
	public List<Order> getAllOrders(){
		return orderRepository.findAll();
	}
	
	
}
