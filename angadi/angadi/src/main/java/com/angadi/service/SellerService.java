package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.SellerException;
import com.angadi.model.Seller;

import java.util.List;

public interface SellerService {

    public Seller addSellerDetails(Seller seller) throws CustomerException;

    public Seller updateSellerDetails(Seller seller) throws SellerException, CustomerException;

    public Seller deleteSellerDetails(Seller seller) throws SellerException, CustomerException;

    public List<Seller> getAllSellerDetails();

}
