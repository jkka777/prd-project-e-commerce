package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.model.Payments;

public interface PaymentsService {

    public Payments addPaymentToOrder(Payments payments, String email) throws CustomerException;
}
