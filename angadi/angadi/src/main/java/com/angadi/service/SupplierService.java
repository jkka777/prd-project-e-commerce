package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.SupplierException;
import com.angadi.model.Suppliers;

import java.util.List;

public interface SupplierService {

    public Suppliers addSupplier(Suppliers suppliers);

    public Suppliers updateSupplier(Suppliers suppliers, String email) throws SupplierException, CustomerException;

    public Suppliers deleteSupplier(Suppliers suppliers, String email) throws SupplierException, CustomerException;

    public List<Suppliers> getAllSupplierDetails();

}
