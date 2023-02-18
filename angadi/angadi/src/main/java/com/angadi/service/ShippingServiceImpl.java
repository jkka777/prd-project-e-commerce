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

    @Autowired
    private CurrentUser currentUser;

    @Override
    public Shipping addShippingDetails(Shipping shipping) throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) return shippingRepository.save(shipping);
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Shipping updateShippingDetails(Shipping shipping) throws CustomerException, ShippingException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Shipping> optional = shippingRepository.findById(shipping.getShippingId());

            if (optional.isPresent()) return shippingRepository.save(shipping);
            throw new ShippingException("No shipping details found with given details!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Shipping cancelShippingDetails(Integer shippingId) throws CustomerException, ShippingException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Shipping> optional = shippingRepository.findById(shippingId);

            if (optional.isPresent()) {

                Shipping s = optional.get();
                shippingRepository.delete(s);
                return s;
            }
            throw new ShippingException("No shipping details found with given shipping id! " + shippingId);
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }
}
