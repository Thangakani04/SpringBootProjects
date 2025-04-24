package com.practice.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer cartId;
    private Double totalAmount;

    @OneToOne(fetch = FetchType.LAZY) 
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cart") 
    private List<CartProduct> cartProducts;

    public Cart() {
        super();
    }

    public Cart(Integer cartId, Double totalAmount, User user, List<CartProduct> cartProducts) {
        super();
        this.cartId = cartId;
        this.totalAmount = totalAmount;
        this.user = user;
        this.cartProducts = cartProducts;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @Override
    public String toString() {
        return "Cart [cartId=" + cartId + ", totalAmount=" + totalAmount + 
               ", user=" + (user != null ? user.getUserId() : "null") +
               ", cartProducts=" + cartProducts + "]";
    }

    public void updateTotalAmount(Double price) {
        this.totalAmount += price;
    }
}
