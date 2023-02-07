package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.model.Customer;
import com.angadi.model.Orders;
import com.angadi.model.Payments;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Payments addPaymentToOrder(Payments payments, String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            payments.setPaymentType(payments.getPaymentType());
            payments.setOrders(payments.getOrders());

            return paymentsRepository.save(payments);
        }
        throw new CustomerException("No Customer found with given email -> " + email);
    }
}
