package com.angadi.service;

import com.angadi.exception.AddressException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Address;
import com.angadi.model.Customer;
import com.angadi.repository.AddressRepository;
import com.angadi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public Set<Address> addAddress(Address address) throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer == null) {
            throw new CustomerException("No customer found with given Email or Invalid user name entered, Please login...");
        } else {
            Set<Address> addressList = customer.getAddresses();
            addressList.add(address);

            customer.setAddresses(addressList);

            address.setCustomer(customer);

            customerRepository.save(customer);

            return addressList;
        }
    }

    @Override
    public Address updateAddress(Address address) throws CustomerException, AddressException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Set<Address> addressList = customer.getAddresses();

            if (!addressList.isEmpty()) {

                for (Address a : addressList) {

                    if (a.getAddressId().equals(address.getAddressId())) {

                        customer.setAddresses(addressList);
                        address.setCustomer(customer);

                        customerRepository.save(customer);
                        return addressRepository.save(address);
                    }
                    throw new AddressException("No address details found with given address!");
                }
            }
            throw new AddressException("Customer does not have any address. Please add one!");
        }
        throw new CustomerException("No customer found with given Email or Invalid user name entered, Please login...");

    }

    @Override
    public Address deleteAddress(Integer addressId) throws CustomerException, AddressException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            List<Address> addresses = addressRepository.findAll();

            if (addresses.size() != 0) {

                for (Address a : addresses) {
                    if (a.getAddressId().equals(addressId)) {
                        addressRepository.delete(a);
                        return a;
                    }
                }
            }
            throw new AddressException("No address found with given details!");
        }
        throw new CustomerException("No customer found with given Email or Invalid user name entered, Please login...");
    }

    @Override
    public Set<Address> getAllAddressOfaUser() throws CustomerException, AddressException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Set<Address> addresses = customer.getAddresses();

            if (addresses.size() != 0) {
                return addresses;
            }
            throw new AddressException("No address found currently, please try again!");
        }
        throw new CustomerException("No customer found with given Email or Invalid user name entered, Please login...");
    }


    /* admin specific functionality */
    @Override
    public Set<Address> getAllAddress() throws CustomerException, AddressException {

        return new HashSet<>(addressRepository.findAll());
    }
}
