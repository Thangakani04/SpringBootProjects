package com.fresco.ecommerce.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.ecommerce.config.JwtUtil;
import com.fresco.ecommerce.models.Category;
import com.fresco.ecommerce.models.Product;
import com.fresco.ecommerce.models.User;
import com.fresco.ecommerce.repo.CartProductRepo;
import com.fresco.ecommerce.repo.CartRepo;
import com.fresco.ecommerce.repo.CategoryRepo;
import com.fresco.ecommerce.repo.ProductRepo;
import com.fresco.ecommerce.repo.UserRepo;

@RestController
@RequestMapping("/api/auth/seller")
public class SellerController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartProductRepo cpRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/product")
    public ResponseEntity<Object> postProduct(@AuthenticationPrincipal UserDetails details, @RequestBody Product product) {
        User user = userRepo.findByUsername(details.getUsername()).orElse(null);
        String role = details.getAuthorities().stream().map(autho->autho.getAuthority()).findFirst().orElse(null);
        if(!role.equals("SELLER")) {
        	return ResponseEntity.status(403).build();
        }
        Category cgry = categoryRepo.findByCategoryName(product.getCategory().getCategoryName()).orElse(null);
        if(cgry == null) {
        	Category saved = categoryRepo.save(product.getCategory());
        	product.setCategory(saved);
        }
        else {
        	product.setCategory(cgry);
        }
        product.setSeller(user);
        Product savedProduct = productRepo.save(product);
        try {
        	return ResponseEntity.created(new URI("http://localhost/api/auth/seller/product/" + savedProduct.getProductId())).build();
        	
        }catch(URISyntaxException e){
        	return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/product")
    public ResponseEntity<Object> getAllProducts(@AuthenticationPrincipal UserDetails details) {
    	User user = userRepo.findByUsername(details.getUsername()).orElse(null);
    	List<Product> list = productRepo.findBySellerUserId(user.getUserId());
    	return ResponseEntity.ok(list);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> getProduct(@AuthenticationPrincipal UserDetails details, @PathVariable("productId") int id) {
    	User user = userRepo.findByUsername(details.getUsername()).orElse(null);
    	Product pro = productRepo.findById(id).orElse(null);
    	if(pro==null) {
    		return ResponseEntity.notFound().build();
    	}
    	if(pro.getSeller().getUserId() != user.getUserId()) {
    		return ResponseEntity.notFound().build();
    	}
    	return ResponseEntity.ok(pro);
    }

    @PutMapping("/product")
    public ResponseEntity<Object> putProduct(@AuthenticationPrincipal UserDetails details, @RequestBody Product product) {
        User user = userRepo.findByUsername(details.getUsername()).orElse(null);
        if(product.getProductId()==0) {
        	return ResponseEntity.badRequest().build();
        }
        Product pro = productRepo.findById(product.getProductId()).orElse(null);
        if(pro==null) {
        	return ResponseEntity.notFound().build();
        }
        pro.setPrice(product.getPrice());
        pro.setSeller(user);
        pro.setProductName(product.getProductName());
        Category cgry = categoryRepo.findByCategoryName(product.getCategory().getCategoryName()).orElse(null);
        if(cgry==null) {
        	Category saved = categoryRepo.save(product.getCategory());
        	pro.setCategory(saved);
        }else {
        	pro.setCategory(cgry);
        }
        productRepo.save(pro);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{productId}")
public ResponseEntity<Object> deleteProduct(@AuthenticationPrincipal UserDetails details, @PathVariable("productId") int id) {
    	User user = userRepo.findByUsername(details.getUsername()).orElse(null);
    	Product pro = productRepo.findById(id).orElse(null);
    	if(pro == null) {
    		return ResponseEntity.notFound().build();
    	}
    	if(!(user.getUserId() == pro.getSeller().getUserId())) {
    		return ResponseEntity.notFound().build();
    	}
    	cpRepo.deleteByProduct_ProductId(id);
    	productRepo.deleteById(id);
    	return ResponseEntity.ok().build();
    }
}
