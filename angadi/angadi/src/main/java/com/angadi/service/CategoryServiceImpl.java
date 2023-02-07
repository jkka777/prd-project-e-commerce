package com.angadi.service;

import com.angadi.exception.CategoryException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Category;
import com.angadi.model.Customer;
import com.angadi.repository.CategoryRepository;
import com.angadi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, String email) throws CategoryException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Category c = categoryRepository.findByCategoryName(category.getCategoryName());

            if (c != null) {
                categoryRepository.save(category);
                return category;
            }
            throw new CategoryException("No category found with given details!");
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public Category deleteCategory(Category category, String email) throws CategoryException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Category c = categoryRepository.findByCategoryName(category.getCategoryName());

            if (c != null) {
                categoryRepository.delete(c);
                return c;
            }
            throw new CategoryException("No category found with given details!");
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
