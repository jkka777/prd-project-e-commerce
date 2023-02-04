package com.angadi.service;

import com.angadi.exception.AddressException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Address;

import java.util.Set;

public interface AddressService {

    public Set<Address> addAddress(Address address, String email) throws CustomerException;

    public Address updateAddress(Address address, String email) throws CustomerException, AddressException;

    public Address deleteAddress(Integer addressId, String email) throws CustomerException, AddressException;

    public Set<Address> getAllAddressOfaUser(String email) throws CustomerException, AddressException;
}
