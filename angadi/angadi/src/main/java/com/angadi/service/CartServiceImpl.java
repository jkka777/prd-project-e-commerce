package com.angadi.service;

import com.angadi.exception.CartException;
import com.angadi.exception.CartItemException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Cart;
import com.angadi.model.CartItem;
import com.angadi.model.Customer;
import com.angadi.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public Cart addCartToCustomer(Cart cart) throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {
            customer.setCart(cart);
            cart.setCustomer(customer);

            cartRepository.save(cart);
        }
        throw new CustomerException("No customer found or Invalid user name provided Please login..");
    }

    public Integer getCartTotal() throws CustomerException, CartException, CartItemException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Cart cart = customer.getCart();

            if (cart != null) {
                Set<CartItem> cartItems = cart.getCartItems();

                if (!cartItems.isEmpty()) {

                    Integer total = 0;

                    for (CartItem ci : cartItems) total += ci.getQuantity() * ci.getProduct().getProductPrice();

                    return total;
                }
                throw new CartItemException("Cart is empty, please add products in cart items");
            }
            throw new CartException("No cart found for your account, please add one!");
        }
        throw new CustomerException("No customer found or Invalid user name provided Please login..");
    }

    public Cart getCartDetails() throws CustomerException, CartException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Cart cart = customer.getCart();

            if (cart != null) {
                return cart;
            }
            throw new CartException("Please create cart and add products in it");
        }
        throw new CustomerException("No customer found or Invalid user name provided Please login..");
    }

    @Override
    public Cart clearCart() throws CustomerException {
        return null;
    }
}
