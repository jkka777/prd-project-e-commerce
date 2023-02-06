package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderDetailsException;
import com.angadi.model.*;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public OrderDetails addOrderDetails(OrderDetails orderDetails, String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Orders orders = orderDetails.getOrders();
            Set<OrderDetails> od = new HashSet<>();
            od.add(orderDetails);
            orders.setOrderDetails(od);

            Product product = orderDetails.getProduct();
            product.setOrderDetails(orderDetails);

            Suppliers suppliers = orderDetails.getSuppliers();
            suppliers.setOrderDetails(orderDetails);

            return orderDetailsRepository.save(orderDetails);
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public OrderDetails updateOrderDetails(OrderDetails orderDetails, String email) throws OrderDetailsException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<OrderDetails> optional = orderDetailsRepository.findById(orderDetails.getOrderDetailsId());

            if (optional.isPresent()) {

                OrderDetails od = optional.get();

                Orders orders = orderDetails.getOrders();
                Set<OrderDetails> odSet = new HashSet<>();
                odSet.add(orderDetails);
                orders.setOrderDetails(odSet);

                Product product = orderDetails.getProduct();
                product.setOrderDetails(orderDetails);

                Suppliers suppliers = orderDetails.getSuppliers();
                suppliers.setOrderDetails(orderDetails);

                return orderDetailsRepository.save(orderDetails);
            }
            throw new OrderDetailsException("No details found with given order order details " + orderDetails.getOrderDetailsId());
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public OrderDetails removeOrderDetails(OrderDetails orderDetails, String email) throws OrderDetailsException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<OrderDetails> optional = orderDetailsRepository.findById(orderDetails.getOrderDetailsId());

            if (optional.isPresent()) {

                OrderDetails od = optional.get();
                orderDetailsRepository.delete(od);
                return orderDetails;
            }
            throw new OrderDetailsException("No details found with given order order details " + orderDetails.getOrderDetailsId());
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }
}
