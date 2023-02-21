package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.model.Orders;

import java.util.List;

public interface OrderService {

    public Orders saveOrder(Orders orders, Integer orderItemId, Integer addressId) throws CustomerException;

    public Orders updateOrder(Integer orderId, Integer addressId) throws OrderException, CustomerException;

    public Orders cancelOrder(Integer orderId) throws OrderException, CustomerException;

    public Orders getOrderById(Integer orderId) throws OrderException, CustomerException;

    public List<Orders> getAllOrdersOfCustomer() throws OrderException, CustomerException;

    /* admin specific method */
    public List<Orders> getAllOrders();
}
