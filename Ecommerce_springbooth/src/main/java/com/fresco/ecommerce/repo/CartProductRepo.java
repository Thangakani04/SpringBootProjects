package com.fresco.ecommerce.repo;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresco.ecommerce.models.CartProduct;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct, Integer> {
    @Transactional
	Optional<CartProduct> findByCartUserUserIdAndProductProductId(Integer userId, Integer productId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart_product WHERE product_id = :productId AND cart_id = (SELECT cart_id FROM cart WHERE user_user_id = :userId)", nativeQuery = true)
    void deleteByCartUserUserIdAndProductProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    @Modifying
    @Transactional
    void deleteByProduct_ProductId(int id);
}