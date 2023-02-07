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
import com.angadi.utility.SortByPriceHighToLow;
import com.angadi.utility.SortByPriceLowToHigh;
import com.angadi.utility.SortByRatingHighToLow;
import com.angadi.utility.SortByRatingsLowToHigh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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

        /*
        getting a product by price of high to low from category
        */

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Category c = categoryRepository.findByCategoryName(category);

            if (c != null) {

                Set<Product> products = c.getProducts();

                List<Product> list = new ArrayList<>(products);

                List<Product> resultList = new ArrayList<>();

                for (Long i = minPrice; i <= maxPrice; i++) {
                    for (Product p : list) {
                        if (p.getProductPrice() == i) {
                            resultList.add(p);
                        }
                    }
                }

                resultList.sort(new SortByPriceHighToLow());
                return new HashSet<>(resultList);
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

                List<Product> resultList = new ArrayList<>();

                for (Long i = minPrice; i <= maxPrice; i++) {
                    for (Product p : list) {
                        if (p.getProductPrice() == i) {
                            resultList.add(p);
                        }
                    }
                }

                resultList.sort(new SortByPriceLowToHigh());
                return new HashSet<>(resultList);
            }
        }
        throw new CustomerException("No user found with given email -> " + email);
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByRatingsHighToLow(String category, Double minRating, Double maxRating, String email) throws ProductException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Category c = categoryRepository.findByCategoryName(category);

            if (c != null) {

                Set<Product> products = c.getProducts();

                List<Product> list = new ArrayList<>(products);

                List<Product> resultList = new ArrayList<>();

                for (Double i = minRating; i <= maxRating; i++) {
                    for (Product p : list) {
                        if (p.getProductRatings().equals(i)) {
                            resultList.add(p);
                        }
                    }
                }

                resultList.sort(new SortByRatingHighToLow());
                return new HashSet<>(resultList);
            }
        }
        throw new CustomerException("No user found with given email -> " + email);

    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByRatingsLowToHigh(String category, Double minRating, Double maxRating, String email) throws ProductException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Category c = categoryRepository.findByCategoryName(category);

            if (c != null) {

                Set<Product> products = c.getProducts();

                List<Product> list = new ArrayList<>(products);

                List<Product> resultList = new ArrayList<>();

                for (Double i = minRating; i <= maxRating; i++) {
                    for (Product p : list) {
                        if (p.getProductRatings().equals(i)) {
                            resultList.add(p);
                        }
                    }
                }

                resultList.sort(new SortByRatingsLowToHigh());
                return new HashSet<>(resultList);
            }
        }
        throw new CustomerException("No user found with given email -> " + email);
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByRatings(String category, Double minRating, String email) throws ProductException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Category c = categoryRepository.findByCategoryName(category);

            if (c != null) {

                Set<Product> products = c.getProducts();

                List<Product> list = new ArrayList<>(products);

                List<Product> resultList = (List<Product>) list.stream().filter(product -> product.getProductRatings() >= minRating);
                resultList.sort(new SortByRatingHighToLow());

                return new HashSet<>(resultList);
            }
        }
        throw new CustomerException("No user found with given email -> " + email);
    }

    @Override
    public Double getStockNumberForProduct(String product, String email) throws CustomerException, ProductException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Product p = productRepository.findByProductName(product);

            if (p != null) {
                return p.getProductStock();
            }
            throw new ProductException("No Product available with given product name!");
        }
        throw new CustomerException("No user found with given email -> " + email);
    }
}
