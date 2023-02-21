package com.angadi.service;

import com.angadi.exception.CartException;
import com.angadi.exception.CartItemException;
import com.angadi.exception.CustomerException;
import com.angadi.exception.ProductException;
import com.angadi.model.Cart;
import com.angadi.model.CartItem;
import com.angadi.model.Customer;
import com.angadi.model.Product;
import com.angadi.repository.CartItemRepository;
import com.angadi.repository.CartRepository;
import com.angadi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartItem addToCart(Integer productId, Integer quantity) throws CustomerException, CartException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            CartItem cartItem = new CartItem();

            Cart cart = customer.getCart();

            if (cart != null) {
                cartItem.setCart(cart);

                Set<CartItem> cartItems = new HashSet<>();
                cartItems.add(cartItem);

                cart.setCartItems(cartItems);

                Optional<Product> optional = productRepository.findById(productId);

                if (optional.isPresent()) {
                    Product p = optional.get();

                    cartItem.setProduct(p);

                    cartItem.setQuantity(quantity);

                    return cartItemRepository.save(cartItem);
                }
                throw new ProductException("No product found with given id! Please enter proper product id");
            }
            throw new CartException("Please create cart first!");
        }
        throw new CustomerException("Invalid user name or password entered, Please login!");
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem, Integer quantity) throws CustomerException, CartItemException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<CartItem> optional = cartItemRepository.findById(cartItem.getCartItemId());

            if (optional.isPresent()) {

                CartItem ci = optional.get();

                cartItem.setQuantity(quantity);
                return cartItemRepository.save(cartItem);
            }
            throw new CartItemException("No cart item found with given details!");
        }
        throw new CustomerException("Invalid user name or password entered, Please login!");

    }

    @Override
    public CartItem removeCartItem(CartItem cartItem) throws CustomerException, CartItemException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Cart cart = customer.getCart();
            if (cart != null) {

                Set<CartItem> cartItems = cart.getCartItems();

                cartItems.removeIf(c -> c.equals(cartItem));

                cart.setCartItems(cartItems);

                Optional<CartItem> optional = cartItemRepository.findById(cartItem.getCartItemId());

                if (optional.isPresent()) {

                    CartItem ci = optional.get();

                    cartItemRepository.delete(ci);
                    return cartItem;
                }
                throw new CartItemException("No item found in cart, Please add item to cart!");
            }
            throw new CartException("Please create cart first!");
        }
        throw new CustomerException("Invalid user name or password entered, Please login!");

    }

    @Override
    public Set<CartItem> getCartItems() throws CustomerException, CartException, CartItemException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Cart cart = customer.getCart();

            if (cart != null) {

                if (!cart.getCartItems().isEmpty()) {
                    return cart.getCartItems();
                }
                throw new CartItemException("No Items found in cart please add items to your cart!");
            }
            throw new CartException("Please create cart first!");
        }
        throw new CustomerException("Invalid user name or password entered, Please login!");
    }

    @Override
    public String clearCartItems() throws CustomerException, CartException, CartItemException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Cart cart = customer.getCart();

            if (cart != null) {

                if (!cart.getCartItems().isEmpty()) {

                    cart.getCartItems().clear();

                    return "Oops! you cleared your cart! Please add Items to it..";
                }
                throw new CartItemException("No Items found in cart please add items to your cart!");
            }
            throw new CartException("Please create cart first!");
        }
        throw new CustomerException("Invalid user name or password entered, Please login!");

    }
}
