package com.ecommerce.fiegn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ecommerce.dto.ProductDTO;

@FeignClient(name = "product-service")  // Refers to the service name registered in Eureka
public interface ProductClient {

    // The method that maps to the Product Service's endpoint
    @GetMapping("/products/{productId}")
    ProductDTO getProductById(@PathVariable("productId") Long productId);
}
