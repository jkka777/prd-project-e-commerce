package com.angadi.service;

import com.angadi.exception.AddressException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Address;
import com.angadi.model.Customer;
import com.angadi.repository.AddressRepository;
import com.angadi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class AddressServiceImpl implements AddressService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Set<Address> addAddress(Address address, String email) throws CustomerException {

        Set<Address> addressList = new HashSet<>();
        addressList.add(address);

        Customer customer = customerRepository.findByEmail(email);

        if (customer == null) {
            throw new CustomerException("No customer found with given Email -> " + email);
        } else {
            customer.setAddresses(addressList);
            address.setCustomer(customer);
            customerRepository.save(customer);
        }
        return addressList;
    }

    @Override
    public Address updateAddress(Address address, String email) throws CustomerException, AddressException {

        List<Address> list = addressRepository.findAll();

        if (list.size() != 0) {

            Optional<Address> optional = addressRepository.findById(address.getAddressId());

            if (optional.isPresent()) {

                Address a = optional.get();
                Customer customer = customerRepository.findByEmail(email);

                if (customer.getEmail().equals(a.getCustomer().getEmail())) {

                    Set<Address> addresses = new HashSet<>();
                    addresses.add(address);

                    customer.setAddresses(addresses);

                    addressRepository.save(address);
                    address.setCustomer(customer);

                    return address;
                }
                throw new CustomerException("Customer details did not match with given email -> " + email);
            }
            throw new AddressException("No address found with given details!");
        }
        throw new AddressException("No address found with given details!");
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
