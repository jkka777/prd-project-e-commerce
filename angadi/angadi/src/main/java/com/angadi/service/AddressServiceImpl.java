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

    @Override
    public Set<Address> addAddress(Address address, String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer == null) {
            throw new CustomerException("No customer found with given Email -> " + email);
        } else {
            Set<Address> addressList = customer.getAddresses();
            addressList.add(address);

            customer.setAddresses(addressList);

            address.setCustomer(customer);

            customerRepository.save(customer);
            addressRepository.save(address);

            return addressList;
        }
    }

    @Override
    public Address updateAddress(Address address, String email) throws CustomerException, AddressException {

        Customer customer = customerRepository.findByEmail(email);

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
            throw new AddressException("Customer with given email does not have any address. Please add one!");
        }
        throw new CustomerException("No customer found with given email -> " + email);

    }

    @Override
    public Address deleteAddress(Integer addressId, String email) throws CustomerException, AddressException {

        Customer customer = customerRepository.findByEmail(email);

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
        throw new CustomerException("No Customer details found with given email -> " + email);
    }

    @Override
    public Set<Address> getAllAddressOfaUser(String email) throws CustomerException, AddressException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            List<Address> addresses = addressRepository.findAll();

            if (addresses.size() != 0) {

                Set<Address> set = new HashSet<>(addresses);
                return set;
            }
            throw new AddressException("No address found with given email -> " + email);
        }
        throw new CustomerException("No Customer details found with given email -> " + email);
    }
}
