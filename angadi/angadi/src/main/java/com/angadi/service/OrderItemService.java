package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderItemException;
import com.angadi.model.OrderItem;

public interface OrderItemService {

    public OrderItem addOrderItems(OrderItem orderItem) throws CustomerException;

    public OrderItem updateOrderItems(OrderItem orderItem) throws OrderItemException, CustomerException;

    public OrderItem removeOrderItems(OrderItem orderItem) throws OrderItemException, CustomerException;

    public Double getPriceOfOrderItems(Integer orderDetailsId) throws OrderItemException, CustomerException;

}
