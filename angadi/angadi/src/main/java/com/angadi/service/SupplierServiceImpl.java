package com.angadi.service;

import com.angadi.exception.CategoryException;
import com.angadi.exception.CustomerException;
import com.angadi.exception.SupplierException;
import com.angadi.model.Category;
import com.angadi.model.Customer;
import com.angadi.model.Suppliers;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Suppliers addSupplier(Suppliers suppliers, String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) return supplierRepository.save(suppliers);
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public Suppliers updateSupplier(Suppliers suppliers, String email) throws SupplierException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Suppliers s = supplierRepository.findBySupplierName(suppliers.getSupplierName());

            if (s != null) {
                supplierRepository.save(suppliers);
                return suppliers;
            }
            throw new SupplierException("No Supplier found with given details!");
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public Suppliers deleteSupplier(Suppliers suppliers, String email) throws SupplierException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Suppliers s = supplierRepository.findBySupplierName(suppliers.getSupplierName());

            if (s != null) {
                supplierRepository.delete(suppliers);
                return suppliers;
            }
            throw new SupplierException("No Supplier found with given details!");
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public List<Suppliers> getAllSupplierDetails() {
        return supplierRepository.findAll();
    }
}
