package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderDetailsException;
import com.angadi.exception.OrderException;
import com.angadi.exception.ShippingException;
import com.angadi.model.*;
import com.angadi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShippingRepository shippingRepository;
    @Autowired
    private PaymentsRepository paymentsRepository;
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Override
    public Orders saveOrder(Orders orders, String email) throws CustomerException {

        /* finding the customer */
        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            orders.setOrderDate(LocalDate.now());
            orders.setCustomer(customer);

            /* getting the wallet of customer */
            Wallet wallet = customer.getWallet();

            /* setting the wallet transactions so that order gets payment via wallet */
            WalletTransactionService wts = new WalletTransactionServiceImpl();

            WalletTransactions owts = orders.getWalletTransactions();
            owts.setTransactionTime(LocalDateTime.now());
            owts.setDescription(owts.getDescription());
            owts.setWallet(wallet);

            Set<OrderDetails> orderDetails = orders.getOrderDetails();
            if (orderDetails.isEmpty()) {
                throw new OrderDetailsException("First add products in order details!");
            }
            Integer productPrices = 0;
            for (OrderDetails p : orderDetails) {
                productPrices += p.getProduct().getProductPrice() * p.getQuantity();
            }

            owts.setAmount(productPrices);

            /* saving and setting the wallet transaction for the order */
            WalletTransactions walletTransactions = wts.addTransaction(owts, email);
            orders.setWalletTransactions(walletTransactions);

            /* setting the delivery address for the order */
            orders.setDeliveryAddress(orders.getDeliveryAddress());

            /* setting the shipping company to our order if available */
            List<Shipping> shippingList = shippingRepository.findAll();

            if (shippingList.isEmpty()) {
                throw new ShippingException("No Shipping company is available right now please try again later!");
            }
            orders.setShipping(shippingList.get(0));

            /* setting delivery status as false because order is not been shipped and delivered */
            orders.setDeliveryStatus(false);

            /* setting delivery date for standard 2 days */
            orders.setDeliveryDate(LocalDate.now().plusDays(2));

            /* setting the total order price of all the order details that are in one single order */
            orders.setTotalOrderPrice(productPrices);

            return orderRepository.save(orders);
        }
        throw new CustomerException("No Customer found with given email!");
    }


    /* Update order details such as customer info, payments info, shipping info by providing order id  */

    @Override
    public Orders updateOrder(Orders orders, String email) throws OrderException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Orders> optional = orderRepository.findById(orders.getOrderId());

            if (optional.isPresent()) {

                Orders o = optional.get();

                o.setCustomer(customer);

                Shipping shipping = orders.getShipping();
                shipping.setOrders(orders);
                o.setShipping(shipping);

                orderRepository.save(orders);
                shippingRepository.save(shipping);
                return orders;
            }
            throw new OrderException("No order details found with given order id -> " + orders.getOrderId());
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    /* Cancel/Delete particular order by giving order id  */

    @Override
    public Orders cancelOrder(Integer orderId, String email) throws OrderException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Orders> optional = orderRepository.findById(orderId);

            if (optional.isPresent()) {

                Orders o = optional.get();
                orderRepository.delete(o);
                return o;
            }
            throw new OrderException("No order details found with given order id -> " + orderId);
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
