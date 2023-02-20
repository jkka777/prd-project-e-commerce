package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.ProductException;
import com.angadi.model.Product;

import java.util.Set;

public interface ProductService {

    public Product addProduct(Product product, String categoryName) throws CustomerException;

    public Product updateProduct(Product product) throws ProductException, CustomerException;

    public Product deleteProduct(Product product) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategory(String category) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategoryAndByPriceHighToLow(String category, Integer minPrice, Integer maxPrice) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategoryAndByPriceLowToHigh(String category, Integer minPrice, Integer maxPrice) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategoryAndByRatingsHighToLow(String category, Double minRating, Double maxRating) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategoryAndByRatingsLowToHigh(String category, Double minRating, Double maxRating) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategoryAndByRatings(String category, Double minRating) throws ProductException, CustomerException;

    public Integer getStockNumberForProduct(String product) throws CustomerException, ProductException;
}
