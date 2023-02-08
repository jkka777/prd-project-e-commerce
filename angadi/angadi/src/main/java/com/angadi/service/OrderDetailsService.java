package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderDetailsException;
import com.angadi.model.OrderDetails;

public interface OrderDetailsService {

    public OrderDetails addOrderDetails(OrderDetails orderDetails, String email) throws CustomerException;

    public OrderDetails updateOrderDetails(OrderDetails orderDetails, String email) throws OrderDetailsException, CustomerException;

    public OrderDetails removeOrderDetails(OrderDetails orderDetails, String email) throws OrderDetailsException, CustomerException;

    public Double getPriceOfOrderDetails(Integer orderDetailsId, String email) throws OrderDetailsException, CustomerException;

}
