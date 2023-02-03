package com.angadi.repository;

import com.angadi.model.Category;
import com.angadi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public Category findByCategoryName(String categoryName);
}
