package com.angadi.service;

import com.angadi.exception.CategoryException;
import com.angadi.exception.CustomerException;
import com.angadi.exception.ProductException;
import com.angadi.model.Category;
import com.angadi.model.Customer;
import com.angadi.model.Product;
import com.angadi.repository.CategoryRepository;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product addProduct(Product product, String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Category category = categoryRepository.findByCategoryName(product.getCategory().getCategoryName());

            Set<Product> categoryProducts = category.getProducts();
            categoryProducts.add(product);
            category.setProducts(categoryProducts);
            categoryRepository.save(category);

            product.setCategory(category);
            return productRepository.save(product);
        }
        throw new CustomerException("No user found with given email -> " + email);
    }

    @Override
    public Product updateProduct(Product product, String email) throws ProductException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Product> optional = productRepository.findById(product.getProductId());

            if (optional.isPresent()) {

                Product p = optional.get();

                p.setCategory(product.getCategory());
                String cat = product.getCategory().getCategoryName();

                Category category = categoryRepository.findByCategoryName(cat);

                Set<Product> categoryProducts = category.getProducts();
                categoryProducts.add(product);

                categoryRepository.save(category);

                return productRepository.save(product);
            }
            throw new ProductException("No Product found with the given details!");
        }
        throw new CustomerException("No user found with given email -> " + email);
    }

    @Override
    public Product deleteProduct(Product product, String email) throws ProductException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Product> optional = productRepository.findById(product.getProductId());

            if (optional.isPresent()) {
                Product p = optional.get();
                productRepository.delete(product);
                return p;
            }
            throw new ProductException("No Product found with the given details!");
        }
        throw new CustomerException("No user found with given email -> " + email);
    }

    @Override
    public Set<Product> getAllProductsByCategory(String category, String email) throws ProductException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Category c = categoryRepository.findByCategoryName(category);

            if (c != null) {
                return c.getProducts();
            }
            throw new CategoryException("No Products found with given Category!");
        }
        throw new CustomerException("No user found with given email -> " + email);
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByPriceHighToLow(String category, Long minPrice, Long maxPrice, String email) throws ProductException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Category c = categoryRepository.findByCategoryName(category);

            if (c != null) {

                Set<Product> products = c.getProducts();

                List<Product> list = new ArrayList<>(products);

                List<Product> sortedList = list.stream().
                        sorted(Comparator.comparingLong(Product::getProductPrice).reversed())
                        .toList();

                return new HashSet<>(sortedList);
            }
        }
        throw new CustomerException("No user found with given email -> " + email);
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByPriceLowToHigh(String category, Long minPrice, Long maxPrice, String email) throws ProductException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Category c = categoryRepository.findByCategoryName(category);

            if (c != null) {

                Set<Product> products = c.getProducts();

                List<Product> list = new ArrayList<>(products);

                List<Product> sortedList = list.stream().
                        sorted(Comparator.comparingLong(Product::getProductPrice)).toList();

                return new HashSet<>(sortedList);
            }
        }
        throw new CustomerException("No user found with given email -> " + email);
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByRatingsHighToLow(String category, Double minRating, Double maxRating) throws ProductException, CustomerException {
        return null;
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByRatingsLowToHigh(String category, Double minRating, Double maxRating) throws ProductException, CustomerException {
        return null;
    }

    @Override
    public boolean getStockAvailabilityForAProduct(String product, String email) throws CustomerException, ProductException {
        return false;
    }

    @Override
    public Double getStockNumberForProduct(String product, String email) throws CustomerException, ProductException {
        return null;
    }
}
