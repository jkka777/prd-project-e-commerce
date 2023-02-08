package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.ShippingException;
import com.angadi.model.Customer;
import com.angadi.model.Orders;
import com.angadi.model.Shipping;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.OrderRepository;
import com.angadi.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Shipping addShippingDetails(Shipping shipping, String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Orders orders = shipping.getOrders();
            orders.setShipping(shipping);

            orderRepository.save(orders);
            return shippingRepository.save(shipping);
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public Shipping updateShippingDetails(Shipping shipping, String email) throws CustomerException, ShippingException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Shipping> optional = shippingRepository.findById(shipping.getShippingId());

            if (optional.isPresent()) {

                Shipping s = optional.get();

                Orders orders = shipping.getOrders();
                orders.setShipping(shipping);

                orderRepository.save(orders);

                return shippingRepository.save(shipping);
            }
            throw new ShippingException("No shipping details found with given details!");
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public Shipping cancelShippingDetails(Integer shippingId, String email) throws CustomerException, ShippingException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Shipping> optional = shippingRepository.findById(shippingId);

            if (optional.isPresent()) {

                Shipping s = optional.get();
                shippingRepository.delete(s);
                return s;
            }
            throw new ShippingException("No shipping details found with given shipping id! " + shippingId);
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }
}
