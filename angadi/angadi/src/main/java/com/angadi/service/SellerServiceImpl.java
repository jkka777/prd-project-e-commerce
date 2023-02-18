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

    @Override
    public Seller addSellerDetails(Seller seller, String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) return sellerRepository.save(seller);
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public Seller updateSellerDetails(Seller seller, String email) throws SellerException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Seller s = sellerRepository.findBySellerName(seller.getSupplierName());

            if (s != null) {
                sellerRepository.save(seller);
                return seller;
            }
            throw new SellerException("No Supplier found with given details!");
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public Seller deleteSellerDetails(Seller seller, String email) throws SellerException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Seller s = sellerRepository.findBySellerName(seller.getSupplierName());

            if (s != null) {
                sellerRepository.delete(seller);
                return seller;
            }
            throw new SellerException("No Supplier found with given details!");
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public List<Seller> getAllSellerDetails() {
        return sellerRepository.findAll();
    }
}
