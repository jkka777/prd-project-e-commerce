package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.exception.ProductException;
import com.angadi.model.Product;
import com.angadi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct/{categoryName}/{email}")
    public ResponseEntity<Product> saveProductHandler(@Valid @RequestBody Product product, @PathVariable String categoryName, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(productService.addProduct(product, categoryName, email), HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{email}")
    public ResponseEntity<Product> updateProductHandler(@Valid @RequestBody Product product, @PathVariable String email) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.updateProduct(product, email), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteProduct/{email}")
    public ResponseEntity<Product> deleteProductHandler(@Valid @RequestBody Product product, @PathVariable String email) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.deleteProduct(product, email), HttpStatus.OK);
    }

    @GetMapping("/productsByCategory/{email}/{category}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryHandler(@Valid @PathVariable String email, @PathVariable String category) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategory(category, email), HttpStatus.ACCEPTED);
    }

    @GetMapping("/productsByPriceHighToLow/{email}/{category}/{maxPrice}/{minPrice}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryAndByPriceHighToLowHandler(@Valid @PathVariable String email, @PathVariable String category, @PathVariable Integer maxPrice, @PathVariable Integer minPrice) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndByPriceHighToLow(category, minPrice, maxPrice, email), HttpStatus.OK);
    }

    @GetMapping("/productsByPriceLowToHigh/{email}/{category}/{minPrice}/{maxPrice}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryAndByPriceLowToHighHandler(@Valid @PathVariable String email, @PathVariable String category, @PathVariable Integer minPrice, @PathVariable Integer maxPrice) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndByPriceLowToHigh(category, minPrice, maxPrice, email), HttpStatus.OK);
    }

    @GetMapping("/productsByRatingsHighToLow/{email}/{category}/{maxRating}/{minRating}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryAndByRatingsHighToLowHandler(@Valid @PathVariable String email, @PathVariable String category, @PathVariable Double maxRating, @PathVariable Double minRating) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndByRatingsHighToLow(category, minRating, maxRating, email), HttpStatus.OK);
    }

    @GetMapping("/productsByRatingsLowToHigh/{email}/{category}/{minRating}/{maxRating}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryAndByRatingsLowToHighHandler(@Valid @PathVariable String email, @PathVariable String category, @PathVariable Double minRating, @PathVariable Double maxRating) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndByRatingsLowToHigh(category, minRating, maxRating, email), HttpStatus.OK);
    }

    @GetMapping("/productsByMinRating/{email}/{category}/{minRating}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryAndByRatingHandler(@Valid @PathVariable String email, @PathVariable String category, @PathVariable Double minRating) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndByRatings(category, minRating, email), HttpStatus.OK);
    }

    @GetMapping("/productStock/{email}/{product}")
    public ResponseEntity<Integer> getProductStockNumberHandler(@Valid @PathVariable String email, @PathVariable String product) throws CustomerException, ProductException {
        return new ResponseEntity<>(productService.getStockNumberForProduct(product, email), HttpStatus.OK);
    }
}
