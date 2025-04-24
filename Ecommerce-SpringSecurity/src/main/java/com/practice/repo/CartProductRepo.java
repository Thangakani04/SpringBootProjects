package com.practice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.models.CartProduct;
import com.practice.models.Product;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct, Integer> {
	Optional<CartProduct> findByCartUserUserIdAndProductProductId(Integer userId, Integer productId);

    Iterable<? extends CartProduct> findAllByProduct(Product product);

}