package com.angadi.service;

import com.angadi.exception.AddressException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Address;

import java.util.Set;

public interface AddressService {

    public Set<Address> addAddress(Address address) throws CustomerException;

    public Address updateAddress(Address address) throws CustomerException, AddressException;

    public Address deleteAddress(Integer addressId) throws CustomerException, AddressException;

    public Set<Address> getAllAddressOfaUser() throws CustomerException, AddressException;

    /* admin specific functionality */
    public Set<Address> getAllAddress() throws CustomerException, AddressException;
}
