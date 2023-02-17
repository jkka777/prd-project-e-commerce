package com.angadi.controller;

import com.angadi.exception.CartException;
import com.angadi.exception.CartItemException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Cart;
import com.angadi.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addCart")
    public ResponseEntity<Cart> addCartToCustomerHandler(@Valid @RequestBody Cart cart) throws CustomerException {
        return new ResponseEntity<>(cartService.addCartToCustomer(cart), HttpStatus.CREATED);
    }

    @GetMapping("/getCart")
    public ResponseEntity<Cart> getCustomerCartHandler() throws CustomerException, CartException {
        return new ResponseEntity<>(cartService.getCartDetails(), HttpStatus.OK);
    }

    @GetMapping("/getCartTotal")
    public ResponseEntity<Integer> getCartTotalHandler() throws CustomerException, CartException, CartItemException {
        return new ResponseEntity<>(cartService.getCartTotal(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCart")
    public ResponseEntity<Cart> clearCartHandler() throws CustomerException {
        return new ResponseEntity<>(cartService.clearCart(), HttpStatus.CREATED);
    }
}
