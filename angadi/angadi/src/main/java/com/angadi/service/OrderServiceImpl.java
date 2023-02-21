package com.angadi.service;

import com.angadi.exception.*;
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

    @Autowired
    private CurrentUser currentUser;

    @Override
    public Orders saveOrder(Orders orders) throws CustomerException {

        /* 1. find the customer get the wallet of customer
           2. set the wallet transactions so that order gets payment via wallet (right now app only works with wallet)
           3. save and set the wallet transaction for the order
           4. set the delivery address for the order
           5. set the shipping company to our order if available
           6. set delivery status as false because order is not been shipped and delivered
           7. set delivery date for standard 2 days
           8. set the total order price of all the order details that are in one single order */

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            orders.setOrderDate(LocalDate.now());

            orders.setOrderStatus("SHIPPED");

            Set<OrderItem> orderItems = new HashSet<>();
            orders.setOrderItems(orderItems);

            orders.setCustomer(customer);

            orders.setDeliveryAddress(orders.getDeliveryAddress());

            Payments payments = new Payments();
            PaymentType pt = payments.getPaymentType();
            payments.setPaymentType(pt);
            payments.setPaymentStatus(payments.getPaymentStatus());
            payments.setPaymentDate(LocalDate.now());

            List<Shipping> shippingList = shippingRepository.findAll();

            int productPrices = 0;

            if (pt == PaymentType.WALLET) {

                Wallet wallet = customer.getWallet();
                if (wallet != null) {

                    if (!orderItems.isEmpty()) {
                        for (OrderItem p : orderItems) {
                            productPrices += p.getProduct().getProductPrice() * p.getQuantity();
                            orders.setTotalOrderPrice(productPrices);
                        }
                    }
                    orders.setTotalOrderPrice(0);

                    if (shippingList.isEmpty()) {
                        throw new ShippingException("No Shipping company is available right now please try again later!");
                    }
                    orders.setShipping(shippingList.get(0));

                    orders.setDeliveryDate(LocalDate.now().plusDays(2));

                    return orderRepository.save(orders);

                }
                throw new WalletException("No wallet found! Please add wallet to your account!");

            } else if (pt == PaymentType.COD) {

                if (shippingList.isEmpty()) {
                    throw new ShippingException("No Shipping company is available right now please try again later!");
                }
                orders.setShipping(shippingList.get(0));

                orders.setDeliveryDate(LocalDate.now().plusDays(3));

                orders.setTotalOrderPrice(productPrices + 49);  /* 49 extra charge for cod + handling charges included*/
            }
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    /* Update order details such as customer info, payments info, shipping info by providing order id  */
    @Override
    public Orders updateOrder(Orders orders) throws OrderException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

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
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    /* Cancel/Delete particular order by giving order id  */
    @Override
    public Orders cancelOrder(Integer orderId) throws OrderException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Orders> optional = orderRepository.findById(orderId);

            if (optional.isPresent()) {

                Orders o = optional.get();
                orderRepository.delete(o);
                return o;
            }
            throw new OrderException("No order details found with given order id -> " + orderId);
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    /* Get particular order details by id  */
    @Override
    public Orders getOrderById(Integer orderId) throws OrderException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Orders> optional = orderRepository.findById(orderId);

            if (optional.isPresent()) {

                return optional.get();
            }
            throw new OrderException("No order details found with given order id -> " + orderId);
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    /* Get list of all order details of a customer*/
    @Override
    public List<Orders> getAllOrdersOfCustomer() throws OrderException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Set<Orders> ordersSet = customer.getOrders();

            if (!ordersSet.isEmpty()) {

                return new ArrayList<>(ordersSet);
            }
            throw new OrderException("No order details found, Please add orders first!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    /* Get all the set of order details in database (internal purpose) */
    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}
