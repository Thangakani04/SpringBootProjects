package com.fresco.ecommerce.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.ecommerce.config.JwtUtil;
import com.fresco.ecommerce.models.Cart;
import com.fresco.ecommerce.models.CartProduct;
import com.fresco.ecommerce.models.Category;
import com.fresco.ecommerce.models.Product;
import com.fresco.ecommerce.models.User;
import com.fresco.ecommerce.repo.CartProductRepo;
import com.fresco.ecommerce.repo.CartRepo;
import com.fresco.ecommerce.repo.CategoryRepo;
import com.fresco.ecommerce.repo.ProductRepo;
import com.fresco.ecommerce.repo.UserRepo;

@RestController
@RequestMapping("/api/auth/consumer")
public class ConsumerController {

    @Autowired
    CartRepo crRepo;
    
    @Autowired
    CategoryRepo cgryRepo;
    
    @Autowired
    UserRepo urRepo;
    
    @Autowired
    ProductRepo prRepo;
    
    @Autowired
    CartProductRepo cpRepo;
    


    @GetMapping("/cart")
public ResponseEntity<Object> getCart(@AuthenticationPrincipal UserDetails details) {
    Cart cart = crRepo.findByUserUsername(details.getUsername()).orElse(null);
    return ResponseEntity.ok(cart);
}

    @PostMapping("/cart")
    public ResponseEntity<Object> postCart(@AuthenticationPrincipal UserDetails details, @RequestBody Product product) {
        Cart cart = crRepo.findByUserUsername(details.getUsername()).orElse(null);
		CartProduct cproduct = cart.getCartProducts().stream()
				.filter(cp->cp.getProduct().getProductId() == product.getProductId()).findFirst().orElse(null);
		if(cproduct != null) {
			return ResponseEntity.status(409).build();
		}
		Category cgry = cgryRepo.findByCategoryName(product.getCategory().getCategoryName()).orElse(null);
		if(cgry == null) {
			Category saved = cgryRepo.save(product.getCategory());
			product.setCategory(saved);
		}
		else {
			product.setCategory(cgry);
		}
		CartProduct cpnew = new CartProduct();
		cpnew.setCart(cart);
		cpnew.setQuantity(1);
		cpnew.setProduct(product);
		cart.getCartProducts().add(cpnew);
		cpRepo.save(cpnew);
		crRepo.save(cart);
        return ResponseEntity.ok().build();
        
    }

    @PutMapping("/cart")
    public ResponseEntity<Object> putCart(@AuthenticationPrincipal UserDetails details, @RequestBody CartProduct cartProd) {
        User user = urRepo.findByUsername(details.getUsername()).orElse(null);
        Cart cart = crRepo.findByUserUsername(details.getUsername()).orElse(null);
        if(cart == null) {
        	return ResponseEntity.notFound().build();
        }
        if(cartProd.getQuantity() == 0) {
        	try {
        		cpRepo.deleteByCartUserUserIdAndProductProductId(user.getUserId(), cartProd.getProduct().getProductId());
            	return ResponseEntity.ok().build();
        	}
        	catch(Exception e) {
            	return ResponseEntity.notFound().build();
        	}
        }
        CartProduct cartpro = cart.getCartProducts()
        		.stream().filter(cp->cp.getProduct().getProductId() == cartProd.getProduct().getProductId()).findFirst().orElse(null);
        if(cartpro==null) {
        	Category cgry = cgryRepo.findByCategoryName(cartProd.getProduct().getCategory().getCategoryName()).orElse(null);
        	if(cgry==null) {
        		Category saved = cgryRepo.save(cartProd.getProduct().getCategory());
        		cartProd.getProduct().setCategory(saved);
        	}else {
        		cartProd.getProduct().setCategory(cgry);
        	}
        	cartProd.setCart(cart);
        	prRepo.save(cartProd.getProduct());
        	cpRepo.save(cartProd);
        	return ResponseEntity.ok().build();
        }
        else {
        	cartpro.setQuantity(cartProd.getQuantity());
        	prRepo.save(cartpro.getProduct());
        	cpRepo.save(cartpro);
        	return ResponseEntity.ok().build();
        }

    }


    @DeleteMapping("/cart")
    public ResponseEntity<Object> deleteCart(@AuthenticationPrincipal UserDetails details, @RequestBody Product product) {
        try {
        	User user = urRepo.findByUsername(details.getUsername()).orElse(null);
            Cart cart = crRepo.findByUserUsername(details.getUsername()).orElse(null);

            if (cart == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            CartProduct cproduct = cart.getCartProducts().stream()
                    .filter(cp -> cp.getProduct().getProductId() == product.getProductId()).findFirst().orElse(null);
            if(cproduct == null) {
            	return ResponseEntity.status(404).build();
            }else {
            	 cpRepo.deleteByCartUserUserIdAndProductProductId(user.getUserId(), product.getProductId());
                 return ResponseEntity.ok().build();
            }
           
        }
        catch(InvalidDataAccessApiUsageException e) {
        	return ResponseEntity.status(404).build();
        }
    }

}