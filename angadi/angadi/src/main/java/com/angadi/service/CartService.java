package com.angadi.service;

import com.angadi.exception.CartException;
import com.angadi.exception.CartItemException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Cart;

public interface CartService {

    public Cart addCartToCustomer(Cart cart) throws CustomerException;

    public Integer getCartTotal() throws CustomerException, CartException, CartItemException;

    public Cart getCartDetails() throws CustomerException, CartException;

    public Cart clearCart() throws CustomerException;

}
