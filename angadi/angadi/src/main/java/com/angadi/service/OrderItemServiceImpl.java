package com.angadi.service;

import com.angadi.exception.CartItemException;
import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.exception.OrderItemException;
import com.angadi.model.*;
import com.angadi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public OrderItem addOrderItems(OrderItem orderItem, Integer cartItemId) throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<CartItem> optional = cartItemRepository.findById(cartItemId);
            if (optional.isEmpty()) throw new CartItemException("Cart is empty! please add items to your cart");
            CartItem cartItem = optional.get();
            orderItem.setCartItem(cartItem);
            cartItem.setOrderItem(orderItem);

            orderItem.setQuantity(cartItem.getQuantity());

            return orderItemRepository.save(orderItem);
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public OrderItem updateOrderItems(OrderItem orderItem, Integer quantity) throws OrderItemException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<OrderItem> optional = orderItemRepository.findById(orderItem.getOrderItemId());

            if (optional.isPresent()) {

                OrderItem od = optional.get();

                od.setQuantity(quantity);

                return orderItemRepository.save(orderItem);
            }
            throw new OrderItemException("No details found with given order order details " + orderItem.getOrderItemId());
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public OrderItem removeOrderItems(OrderItem orderItem) throws OrderItemException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<OrderItem> optional = orderItemRepository.findById(orderItem.getOrderItemId());

            if (optional.isPresent()) {

                OrderItem od = optional.get();
                orderItemRepository.delete(od);
                return orderItem;
            }
            throw new OrderItemException("No details found with given order order details " + orderItem.getOrderItemId());
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Integer getPriceOfOrderItems(Integer orderDetailsId) throws OrderItemException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<OrderItem> optional = orderItemRepository.findById(orderDetailsId);

            if (optional.isPresent()) {

                OrderItem od = optional.get();

                Integer productPrice = od.getCartItem().getProduct().getProductPrice();

                return productPrice * od.getQuantity();
            }
            throw new OrderItemException("No data found with given order details!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }
}
