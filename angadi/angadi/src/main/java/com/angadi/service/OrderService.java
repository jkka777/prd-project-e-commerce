package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.model.Orders;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    public Orders saveOrder(Orders orders, Integer orderItemId, Integer addressId) throws CustomerException;

    public Orders updateOrder(Integer orderId, Integer addressId) throws OrderException, CustomerException;

    public Orders cancelOrder(Integer orderId) throws OrderException, CustomerException;

    public Orders getOrderById(Integer orderId) throws OrderException, CustomerException;

    public List<Orders> getAllOrdersOfCustomer() throws OrderException, CustomerException;

    public List<Orders> getAllOrdersBetweenDates(LocalDate dateFrom, LocalDate dateTo) throws OrderException, CustomerException;

    public List<Orders> getAllOrdersByDeliveryAddress(Integer addressId) throws CustomerException, OrderException;

    /* admin specific method */
    public List<Orders> getAllOrders();
}
