package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderItemException;
import com.angadi.model.OrderItem;

public interface OrderItemService {

    public OrderItem addOrderDetails(OrderItem orderItem, String email) throws CustomerException;

    public OrderItem updateOrderDetails(OrderItem orderItem, String email) throws OrderItemException, CustomerException;

    public OrderItem removeOrderDetails(OrderItem orderItem, String email) throws OrderItemException, CustomerException;

    public Double getPriceOfOrderDetails(Integer orderDetailsId, String email) throws OrderItemException, CustomerException;

}
