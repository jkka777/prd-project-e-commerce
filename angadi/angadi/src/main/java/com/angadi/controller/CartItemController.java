package com.angadi.controller;

import com.angadi.exception.CartException;
import com.angadi.exception.CartItemException;
import com.angadi.exception.CustomerException;
import com.angadi.model.CartItem;
import com.angadi.model.Product;
import com.angadi.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/addItem")
    public ResponseEntity<CartItem> addProductToCartItemHandler(@Valid @RequestBody Product product, @PathVariable Integer quantity) throws CustomerException, CartException {
        return new ResponseEntity<>(cartItemService.addToCart(product, quantity), HttpStatus.CREATED);
    }

    @PutMapping("/updateItem")
    public ResponseEntity<CartItem> updateProductInCartItemHandler(@Valid @RequestBody CartItem cartItem, @PathVariable Integer quantity) throws CustomerException, CartException {
        return new ResponseEntity<>(cartItemService.updateCartItem(cartItem, quantity), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/removeItem")
    public ResponseEntity<CartItem> removeProductInCartItemHandler(@Valid @RequestBody CartItem cartItem) throws CustomerException, CartException {
        return new ResponseEntity<>(cartItemService.removeCartItem(cartItem), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getCartItems")
    public ResponseEntity<Set<CartItem>> getCartItemsItemHandler() throws CustomerException, CartException, CartItemException {
        return new ResponseEntity<>(cartItemService.getCartItems(), HttpStatus.OK);
    }

    @GetMapping("/clearCart")
    public ResponseEntity<String> clearCartHandler() throws CustomerException, CartException, CartItemException {
        return new ResponseEntity<>(cartItemService.clearCartItems(), HttpStatus.OK);
    }
}
