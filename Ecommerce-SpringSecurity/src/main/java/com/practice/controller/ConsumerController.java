package com.practice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.config.JwtUtilPractice;
import com.practice.models.Cart;
import com.practice.models.CartProduct;
import com.practice.models.Product;
import com.practice.repo.CartRepo;
import com.practice.repo.ProductRepo;

@RestController
@RequestMapping(value = "/api/auth/consumer")
public class ConsumerController {
	
	@Autowired
	private JwtUtilPractice jwtutil;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private ProductRepo prodrepo;
	
	@GetMapping("/cart")
	public ResponseEntity<Cart> getCart(@RequestHeader("JWT") String jwt)
	{
		String username = extractfromjwt(jwt);
		Optional<Cart> optcart = cartRepo.findByUserUsername(username);
		
		if(optcart.isPresent()) {
			Cart cart = optcart.get();
			return ResponseEntity.ok(cart);
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@PostMapping("/cart")
	public ResponseEntity<Object> postCart(@RequestHeader("JWT") String jwt , @RequestBody Product product){
		String username = extractfromjwt(jwt);
		Optional<Cart> optcart = cartRepo.findByUserUsername(username);
		Optional<Product> optprod = prodrepo.findById(product.getProductId());
		
		if(optcart.isPresent() && optprod.isPresent()) {
			Cart cart = optcart.get();
			Product prod = optprod.get();
			
			List<CartProduct> cartproducts = cart.getCartProducts();
			boolean productis = false;
			for(CartProduct cartproductss : cartproducts) {
				if(cartproductss.getProduct().equals(prod)) {
					productis=true;
					break;
				}
			}
			
			if(!productis) {
				CartProduct cp = new CartProduct();
				cp.setCart(cart);
				cp.setProduct(prod);
				cp.setQuantity(1);
				cartproducts.add(cp);
				cart.updateTotalAmount(prod.getPrice());
				cartRepo.save(cart);
				return ResponseEntity.ok(cart);
			}
			else {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	
	
	
	
	
	
	
	private String extractfromjwt(String jwt) {
		// TODO Auto-generated method stub
		return jwt!=null ? jwtutil.extractUsername(jwt) : null;
	}

}
