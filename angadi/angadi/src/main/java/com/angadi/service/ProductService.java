package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.ProductException;
import com.angadi.model.Product;

import java.util.Set;

public interface ProductService {

    public Product addProduct(Product product, String email) throws CustomerException;

    public Product updateProduct(Product product, String email) throws ProductException, CustomerException;

    public Product deleteProduct(Product product, String email) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategory(String category, String email) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategoryAndByPriceHighToLow(String category, Long minPrice, Long maxPrice, String email) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategoryAndByPriceLowToHigh(String category, Long minPrice, Long maxPrice, String email) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategoryAndByRatingsHighToLow(String category, Double minRating, Double maxRating) throws ProductException, CustomerException;

    public Set<Product> getAllProductsByCategoryAndByRatingsLowToHigh(String category, Double minRating, Double maxRating) throws ProductException, CustomerException;

    public boolean getStockAvailabilityForAProduct(String product, String email) throws CustomerException, ProductException;

    public Double getStockNumberForProduct(String product, String email) throws CustomerException, ProductException;
}
