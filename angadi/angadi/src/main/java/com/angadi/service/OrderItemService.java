package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderItemException;
import com.angadi.model.OrderItem;

public interface OrderItemService {

    public OrderItem addOrderItems(OrderItem orderItem, Integer cartItemId) throws CustomerException;

    public OrderItem updateOrderItems(OrderItem orderItem, Integer quantity) throws OrderItemException, CustomerException;

    public OrderItem removeOrderItems(OrderItem orderItem) throws OrderItemException, CustomerException;

    public Integer getPriceOfOrderItems(Integer orderDetailsId) throws OrderItemException, CustomerException;

}
