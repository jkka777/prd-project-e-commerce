package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.model.Orders;

import java.util.List;

public interface OrderService {

    public Orders updateOrder(Orders orders, String email) throws OrderException, CustomerException;

    public Orders cancelOrder(Integer orderId, String email) throws OrderException, CustomerException;

    public Orders getOrderById(Integer orderId, String email) throws OrderException, CustomerException;

    public List<Orders> getOrdersByCustomerEmail(String email) throws OrderException, CustomerException;

    public List<Orders> getAllOrders();
}
