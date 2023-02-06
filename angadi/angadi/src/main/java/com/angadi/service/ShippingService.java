package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.ShippingException;
import com.angadi.model.Shipping;

public interface ShippingService {

    public Shipping addShippingDetails(Shipping shipping, String email);

    public Shipping updateShippingDetails(Shipping shipping, String email) throws CustomerException, ShippingException;

    public Shipping cancelShippingDetails(Shipping shipping, String email) throws CustomerException, ShippingException;
}
