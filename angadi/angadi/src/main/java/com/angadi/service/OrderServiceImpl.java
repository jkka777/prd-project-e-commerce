package com.angadi.service;

import com.angadi.exception.*;
import com.angadi.model.*;
import com.angadi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public Orders saveOrder(Orders orders, Integer orderItemId, Integer addressId) throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<OrderItem> optional = orderItemRepository.findById(orderItemId);
            if (optional.isEmpty())
                throw new OrderItemException("No Item found with given id please add item in your cart");
            OrderItem orderItem = optional.get();

            orders.setOrderDate(LocalDate.now());
            orders.setOrderStatus("SHIPPED");

            Set<OrderItem> orderItems = new HashSet<>();
            orderItems.add(orderItem);
            orderItem.setOrders(orders);
            orders.setOrderItems(orderItems);

            int orderPrice = 0;
            for (OrderItem oi : orderItems) {
                orderPrice += oi.getCartItem().getProduct().getProductPrice() * oi.getCartItem().getQuantity();
            }

            orders.setTotalOrderPrice(orderPrice);

            orders.setCustomer(customer);

            Set<Orders> customerOrders = new HashSet<>();
            customerOrders.add(orders);
            customer.setOrders(customerOrders);

            Optional<Address> addressOptional = addressRepository.findById(addressId);
            if (addressOptional.isEmpty())
                throw new AddressException("No address found! please add address before you proceed to order");
            orders.setDeliveryAddress(addressOptional.get());

            Payments payments = orders.getPayments();
            PaymentType pt = payments.getPaymentType();

            List<Shipping> shippingList = shippingRepository.findAll();

            if (pt == PaymentType.WALLET) {

                payments.setPaymentType(PaymentType.WALLET);
                payments.setPaymentDate(LocalDate.now());
                payments.setPaymentStatus(orders.getPayments().getPaymentStatus());
                payments.setOrders(orders);
                orders.setPayments(payments);

                LocalDate today = LocalDate.now();
                orders.setDeliveryDate(today.plusDays(2));

            } else if (pt == PaymentType.COD) {

                payments.setPaymentType(PaymentType.COD);
                payments.setPaymentDate(LocalDate.now());
                payments.setPaymentStatus(orders.getPayments().getPaymentStatus());
                payments.setOrders(orders);
                orders.setPayments(payments);

                LocalDate today = LocalDate.now();
                orders.setDeliveryDate(today.plusDays(3));
            }

            orders.setTotalOrderPrice(orderPrice);

            paymentsRepository.save(payments);

            if (shippingList.isEmpty()) {
                throw new ShippingException("No Shipping company is available right now please try again later!");
            }
            orders.setShipping(shippingList.get(0));

            return orderRepository.save(orders);
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    /* Update order details such as customer info, payments info, shipping info by providing order id  */
    @Override
    public Orders updateOrder(Integer orderId, Integer addressId) throws OrderException, CustomerException {

        /*
        1. get the order by its id
        2. set date, status, orderItems, total order price from the orderItems
        3. assign address to order by customers saved address
        4. add payments method, shipping details, and estimated delivery date
        */

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            /*Optional<Orders> optional = orderRepository.findById(orderId);*/

            Set<Orders> orders = customer.getOrders();

            Orders o = null;

            for (Orders or : orders) {
                if (or.getOrderId().equals(orderId)) o = or;
            }

            if (o != null) {

                o.setOrderDate(LocalDate.now());
                o.setOrderStatus("SHIPPED");

                Set<OrderItem> orderItemSet = o.getOrderItems();
                int orderPrice = 0;
                for (OrderItem orderItem : orderItemSet) {
                    orderPrice += orderItem.getCartItem().getProduct().getProductPrice() * orderItem.getQuantity();
                }

                o.setTotalOrderPrice(orderPrice);
                o.setOrderItems(orderItemSet);
                o.setCustomer(customer);
                Optional<Address> addressOptional = addressRepository.findById(addressId);
                if (addressOptional.isEmpty())
                    throw new AddressException("No address found with given id, please add address");
                o.setDeliveryAddress(addressOptional.get());

                Payments payments = new Payments();
                PaymentType pt = payments.getPaymentType();
                payments.setPaymentType(pt);
                payments.setPaymentStatus(payments.getPaymentStatus());
                payments.setPaymentDate(LocalDate.now());

                List<Shipping> shippingList = shippingRepository.findAll();

                if (pt == PaymentType.WALLET) {

                    if (shippingList.isEmpty()) {
                        throw new ShippingException("No Shipping company is available right now please try again later!");
                    }
                    o.setShipping(shippingList.get(0));

                    o.setDeliveryDate(LocalDate.now().plusDays(2));
                    return orderRepository.save(o);

                } else if (pt == PaymentType.COD) {

                    if (shippingList.isEmpty()) {
                        throw new ShippingException("No Shipping company is available right now please try again later!");
                    }
                    o.setShipping(shippingList.get(0));

                    o.setDeliveryDate(LocalDate.now().plusDays(3));

                    o.setTotalOrderPrice(orderPrice + 49);  /* 49 extra charge for cod + handling charges included*/
                    return orderRepository.save(o);
                }
            }
            throw new OrderException("No order details found with given order id -> " + orderId);
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

            if (optional.isEmpty())
                throw new OrderException("No order details found with given order id -> " + orderId);
            return optional.get();
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
