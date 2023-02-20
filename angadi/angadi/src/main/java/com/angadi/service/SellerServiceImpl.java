package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.SellerException;
import com.angadi.model.Customer;
import com.angadi.model.Seller;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public Seller addSellerDetails(Seller seller) throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) return sellerRepository.save(seller);
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Seller updateSellerDetails(Seller seller) throws SellerException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Seller s = sellerRepository.findBySellerName(seller.getSellerName());

            if (s != null) {
                sellerRepository.save(seller);
                return seller;
            }
            throw new SellerException("No Supplier found with given details!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Seller deleteSellerDetails(Seller seller) throws SellerException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Seller s = sellerRepository.findBySellerName(seller.getSellerName());

            if (s != null) {
                sellerRepository.delete(seller);
                return seller;
            }
            throw new SellerException("No Supplier found with given details!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public List<Seller> getAllSellerDetails() {
        return sellerRepository.findAll();
    }
}
