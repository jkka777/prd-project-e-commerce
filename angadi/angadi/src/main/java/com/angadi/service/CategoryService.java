package com.angadi.service;

import com.angadi.exception.CategoryException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Category;

import java.util.List;

public interface CategoryService {

    public Category addCategory(Category category, String email) throws CustomerException;

    public Category updateCategory(Category category, String email) throws CategoryException, CustomerException;

    public Category deleteCategory(Category category, String email) throws CategoryException, CustomerException;

    public List<Category> getAllCategory();
}
