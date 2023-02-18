package com.angadi.service;

import com.angadi.exception.CustomerException;
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

    @Override
    public OrderItem addOrderDetails(OrderItem orderItem, String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Orders orders = orderItem.getOrders();
            Set<OrderItem> od = new HashSet<>();
            od.add(orderItem);
            orders.setOrderItems(od);

            Product product = orderItem.getProduct();
            product.setOrderItem(orderItem);

            Seller seller = orderItem.getSeller();
            seller.setOrderItem(orderItem);

            return orderItemRepository.save(orderItem);
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public OrderItem updateOrderDetails(OrderItem orderItem, String email) throws OrderItemException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

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
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public OrderItem removeOrderDetails(OrderItem orderItem, String email) throws OrderItemException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<OrderItem> optional = orderItemRepository.findById(orderItem.getOrderItemId());

            if (optional.isPresent()) {

                OrderItem od = optional.get();
                orderItemRepository.delete(od);
                return orderItem;
            }
            throw new OrderItemException("No details found with given order order details " + orderItem.getOrderItemId());
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public Double getPriceOfOrderDetails(Integer orderDetailsId, String email) throws OrderItemException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

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
        throw new CustomerException("No Customer found with given email -> " + email);
    }
}
