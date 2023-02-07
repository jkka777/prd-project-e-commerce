package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.model.*;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    /* Update order details such as customer info, payments info, shipping info by providing order id  */

    @Override
    public Orders updateOrder(Orders orders, String email) throws OrderException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Orders> optional = orderRepository.findById(orders.getOrderId());

            if (optional.isPresent()) {

                Orders o = optional.get();

                o.setCustomer(customer);

                Payments payments = orders.getPayments();
                payments.setOrders(o);
                o.setPayments(payments);

                Shipping shipping = orders.getShipping();
                shipping.setOrders(orders);
                o.setShipping(shipping);

                orderRepository.save(orders);
                return orders;
            }
            throw new OrderException("No order details found with given order id -> " + orders.getOrderId());
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    /* Cancel/Delete particular order by giving order id  */

    @Override
    public Orders cancelOrder(Orders orders, String email) throws OrderException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Orders> optional = orderRepository.findById(orders.getOrderId());

            if (optional.isPresent()) {

                Orders o = optional.get();
                orderRepository.delete(o);
                return orders;
            }
            throw new OrderException("No order details found with given order id -> " + orders.getOrderId());
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    /* Get particular order details by id  */

    @Override
    public Orders getOrderById(Integer orderId, String email) throws OrderException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Orders> optional = orderRepository.findById(orderId);

            if (optional.isPresent()) {

                return optional.get();
            }
            throw new OrderException("No order details found with given order id -> " + orderId);
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }


    /* Get list of all order details for a customer by providing customer email */

    @Override
    public List<Orders> getOrdersByCustomerEmail(String email) throws OrderException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Set<Orders> ordersSet = customer.getOrders();

            if (!ordersSet.isEmpty()) {

                return new ArrayList<>(ordersSet);
            }
            throw new OrderException("No order details found with given customer email -> " + email);
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    /* Get all the set of order details in database (internal purpose) */

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}
