package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;

@RestController
@RequestMapping("/products")  //base url
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	//create a product
	
	@PostMapping
	public Product addProduct(@RequestBody Product product)  // the product which we will give in post man will come to this request body, from this body we need to fetch our product details
	{
		return productRepository.save(product);		
	}
	
	//Get all products
	@GetMapping
	public List<Product> getAllProducts(){
		return productRepository.findAll();
		
	}
	
	//Get product by id
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Long productId){
		Product product =  productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with id" + productId));  //orElseThrow is used for if we pass id as 1 , if 1 is not present in database it will throw runtime exception
		return ResponseEntity.ok(product);
		
		
	}
	
	

}
