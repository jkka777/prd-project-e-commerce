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
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public Category addCategory(Category category) throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {
            category.setCategoryName(category.getCategoryName().toUpperCase());
            return categoryRepository.save(category);
        }
        throw new CustomerException("Invalid user name or password or Please login first!");
    }

    @Override
    public Category updateCategory(Category category) throws CategoryException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Category> optional = categoryRepository.findByCategoryName(category.getCategoryName());

            if (optional.isPresent()) {

                Category c = optional.get();
                categoryRepository.save(category);
                return category;
            }
            throw new CategoryException("No category found with given details!");
        }
        throw new CustomerException("Invalid user name or password or Please login first!");
    }

    @Override
    public Category deleteCategory(Category category) throws CategoryException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Category> optional = categoryRepository.findByCategoryName(category.getCategoryName());

            if (optional.isPresent()) {

                Category c = optional.get();
                categoryRepository.delete(c);
                return c;
            }
            throw new CategoryException("No category found with given details!");
        }
        throw new CustomerException("Invalid user name or password or Please login first!");
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
