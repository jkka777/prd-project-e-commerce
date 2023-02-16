package com.angadi.repository;

import com.angadi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    public Product findByProductName(String productName);

    @Query("SELECT P FROM Product P WHERE P.category = :category AND P.productPrice BETWEEN :min AND :max")
    public List<Product> findByCategoryAndPriceBetween(@Param("category") String category, @Param("min") Integer min, @Param("max") Integer max);

    @Query("SELECT P FROM Product P WHERE P.category = :category AND P.productRatings BETWEEN :min AND :max")
    public List<Product> findByCategoryAndRatingBetween(@Param("category") String category, @Param("min") Double min, @Param("max") Double max);

}
