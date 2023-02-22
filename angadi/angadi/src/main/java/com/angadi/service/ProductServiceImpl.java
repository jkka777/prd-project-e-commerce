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

    @Autowired
    private CurrentUser currentUser;

    @Override
    public Product addProduct(Product product, String categoryName) throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Category> optional = categoryRepository.findByCategoryName(categoryName.toUpperCase());

            if (optional.isPresent()) {

                Category category = optional.get();

                Set<Product> set = new HashSet<>();
                set.add(product);

                category.setProducts(set);
                product.setCategory(category);

                customer.setProduct(product);
                product.setCustomer(customer);

                return productRepository.save(product);
            }
            throw new CategoryException("No Category found with given category name.. please add one before you save the product");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Product updateProduct(Product product) throws ProductException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Product> optional = productRepository.findById(product.getProductId());

            if (optional.isPresent()) {

                Product p = optional.get();
                product.setCategory(p.getCategory());
                return productRepository.save(product);
            }
            throw new ProductException("No Product found with the given details!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Product deleteProduct(Product product) throws ProductException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Product> optional = productRepository.findById(product.getProductId());

            if (optional.isPresent()) {

                Product p = optional.get();
                productRepository.delete(product);
                return p;
            }
            throw new ProductException("No Product found with the given details!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Set<Product> getAllProductsByCategory(String category) throws ProductException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Category> optional = categoryRepository.findByCategoryName(category.toUpperCase());

            if (optional.isPresent()) {
                return optional.get().getProducts();
            }
            throw new CategoryException("No Products found with given Category!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByPriceHighToLow(String category, Integer minPrice, Integer maxPrice) throws ProductException, CustomerException {

        /* Get product by price of high to low from category */

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Category> optional = categoryRepository.findByCategoryName(category.toUpperCase());

            if (optional.isPresent()) {

                Category c = optional.get();
                String cat = String.valueOf(c);

                Set<Product> products = c.getProducts();

                List<Product> dList = productRepository.findByCategoryAndPriceBetween(cat, minPrice, maxPrice);
                if (dList.isEmpty()) {
                    throw new ProductException("No products found right now, please try again some time!");
                }
                dList.sort(new SortByRatingHighToLow());
                return new HashSet<>(dList);

                /*List<Product> list = new ArrayList<>(products);

                List<Product> resultList = new ArrayList<>();

                for (Integer i = minPrice; i <= maxPrice; i++) {
                    for (Product p : list) if (p.getProductPrice().equals(i)) resultList.add(p);
                }

                resultList.sort(new SortByPriceHighToLow());
                return new HashSet<>(resultList);*/
            }
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByPriceLowToHigh(String category, Integer minPrice, Integer maxPrice) throws ProductException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Category> optional = categoryRepository.findByCategoryName(category.toUpperCase());

            if (optional.isPresent()) {

                Category c = optional.get();
                String cat = String.valueOf(c);

                Set<Product> products = c.getProducts();

                List<Product> dList = productRepository.findByCategoryAndPriceBetween(cat, minPrice, maxPrice);
                if (dList.isEmpty()) {
                    throw new ProductException("No products found right now, please try again some time!");
                }
                dList.sort(new SortByPriceLowToHigh());
                return new HashSet<>(dList);

                /*List<Product> list = new ArrayList<>(products);

                List<Product> resultList = new ArrayList<>();

                for (Integer i = minPrice; i <= maxPrice; i++) {
                    for (Product p : list) if (p.getProductPrice() == i) resultList.add(p);
                }

                resultList.sort(new SortByPriceLowToHigh());
                return new HashSet<>(resultList);*/
            }
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByRatingsHighToLow(String category, Double minRating, Double maxRating) throws ProductException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Category> optional = categoryRepository.findByCategoryName(category.toUpperCase());

            if (optional.isPresent()) {

                Category c = optional.get();
                String cat = String.valueOf(c);

                Set<Product> products = c.getProducts();

                List<Product> dList = productRepository.findByCategoryAndRatingBetween(cat, minRating, maxRating);
                if (dList.isEmpty()) {
                    throw new ProductException("No products found for the provided criteria");
                }
                dList.sort(new SortByRatingHighToLow());
                return new HashSet<>(dList);

                /*List<Product> list = new ArrayList<>(products);

                List<Product> resultList = new ArrayList<>();

                for (Double i = minRating; i <= maxRating; i++) {
                    for (Product p : list) if (p.getProductRatings().equals(i)) resultList.add(p);
                }

                resultList.sort(new SortByRatingHighToLow());
                return new HashSet<>(resultList);*/
            }
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByRatingsLowToHigh(String category, Double minRating, Double maxRating) throws ProductException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Category> optional = categoryRepository.findByCategoryName(category.toUpperCase());

            if (optional.isPresent()) {

                Category c = optional.get();
                String cat = String.valueOf(c);

                Set<Product> products = c.getProducts();

                List<Product> dList = productRepository.findByCategoryAndRatingBetween(cat, minRating, maxRating);
                if (dList.isEmpty()) {
                    throw new ProductException("No products found for the provided criteria");
                }
                dList.sort(new SortByRatingsLowToHigh());
                return new HashSet<>(dList);

                /*List<Product> list = new ArrayList<>(products);

                List<Product> resultList = new ArrayList<>();

                for (Double i = minRating; i <= maxRating; i++) {
                    for (Product p : list) if (p.getProductRatings().equals(i)) resultList.add(p);
                }

                resultList.sort(new SortByRatingsLowToHigh());
                return new HashSet<>(resultList);*/
            }
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Set<Product> getAllProductsByCategoryAndByRatings(String category, Double minRating) throws ProductException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Category> optional = categoryRepository.findByCategoryName(category.toUpperCase());

            if (optional.isPresent()) {

                Category c = optional.get();

                Set<Product> products = c.getProducts();

                List<Product> list = new ArrayList<>(products);

                List<Product> resultList = new ArrayList<>();

                for (Product p : list) if (p.getProductRatings() >= minRating) resultList.add(p);

                resultList.sort(new SortByRatingHighToLow());

                return new HashSet<>(resultList);
            }
            throw new ProductException("No products available right now, Please try again later!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Integer getStockNumberForProduct(String product) throws CustomerException, ProductException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Product p = productRepository.findByProductName(product.toUpperCase());

            if (p != null) {
                return p.getProductStock();
            }
            throw new ProductException("No Product available with given product name!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }
}
