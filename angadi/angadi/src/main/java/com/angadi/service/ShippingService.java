package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.ShippingException;
import com.angadi.model.Shipping;

public interface ShippingService {

    public Shipping addShippingDetails(Shipping shipping) throws CustomerException;

    public Shipping updateShippingDetails(Shipping shipping) throws CustomerException, ShippingException;

    public Shipping cancelShippingDetails(Integer shippingId) throws CustomerException, ShippingException;
}
