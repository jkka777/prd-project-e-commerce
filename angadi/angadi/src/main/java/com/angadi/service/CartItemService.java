package com.angadi.service;

import com.angadi.exception.CartException;
import com.angadi.exception.CartItemException;
import com.angadi.exception.CustomerException;
import com.angadi.model.CartItem;
import com.angadi.model.Product;

import java.util.Set;

public interface CartItemService {

    public CartItem addToCart(Integer productId, Integer quantity) throws CustomerException, CartException;

    public CartItem updateCartItem(CartItem cartItem, Integer quantity) throws CustomerException, CartItemException;

    public CartItem removeCartItem(CartItem cartItem) throws CustomerException, CartItemException;

    public Set<CartItem> getCartItems() throws CustomerException, CartException, CartItemException;

    public String clearCartItems() throws CustomerException, CartException, CartItemException;
}
