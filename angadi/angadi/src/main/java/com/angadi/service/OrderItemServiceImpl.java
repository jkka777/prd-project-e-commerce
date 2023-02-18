package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.exception.OrderItemException;
import com.angadi.model.*;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public OrderItem addOrderItems(OrderItem orderItem) throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Orders orders = orderItem.getOrders();

            if (orders == null) {
                throw new OrderException("Please create order instance so that you can add items in it.");
            }

            Set<OrderItem> od = new HashSet<>();
            od.add(orderItem);
            orders.setOrderItems(od);

            orderItem.setOrders(orders);

            orderItem.setProduct(orderItem.getProduct());

            orderItem.setSeller(orderItem.getSeller());

            return orderItemRepository.save(orderItem);
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public OrderItem updateOrderItems(OrderItem orderItem) throws OrderItemException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<OrderItem> optional = orderItemRepository.findById(orderItem.getOrderItemId());

            if (optional.isPresent()) {

                OrderItem od = optional.get();

                Orders orders = orderItem.getOrders();
                Set<OrderItem> odSet = new HashSet<>();
                odSet.add(orderItem);
                orders.setOrderItems(odSet);

                Product product = orderItem.getProduct();
                product.setOrderItem(orderItem);

                Seller seller = orderItem.getSeller();
                seller.setOrderItem(orderItem);

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
    public Double getPriceOfOrderItems(Integer orderDetailsId) throws OrderItemException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<OrderItem> optional = orderItemRepository.findById(orderDetailsId);

            if (optional.isPresent()) {

                OrderItem od = optional.get();

                Product product = od.getProduct();
                Double productPrice = (double) product.getProductPrice();

                return productPrice * od.getQuantity();
            }
            throw new OrderItemException("No data found with given order details!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }
}
