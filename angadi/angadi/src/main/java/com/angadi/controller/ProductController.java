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

    @PostMapping("/addProduct/{categoryName}")
    public ResponseEntity<Product> saveProductHandler(@Valid @RequestBody Product product, @PathVariable String categoryName) throws CustomerException {
        return new ResponseEntity<>(productService.addProduct(product, categoryName), HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProductHandler(@Valid @RequestBody Product product) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Product> deleteProductHandler(@Valid @RequestBody Product product) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.deleteProduct(product), HttpStatus.OK);
    }

    @GetMapping("/allProductsByCategory/{category}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryHandler(@Valid @PathVariable String category) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategory(category), HttpStatus.ACCEPTED);
    }

    @GetMapping("/productsByPriceHighToLow/{category}/{maxPrice}/{minPrice}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryAndByPriceHighToLowHandler(@Valid @PathVariable String category, @PathVariable Integer maxPrice, @PathVariable Integer minPrice) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndByPriceHighToLow(category, minPrice, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/productsByPriceLowToHigh/{category}/{minPrice}/{maxPrice}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryAndByPriceLowToHighHandler(@Valid @PathVariable String category, @PathVariable Integer minPrice, @PathVariable Integer maxPrice) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndByPriceLowToHigh(category, minPrice, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/productsByRatingsHighToLow/{category}/{maxRating}/{minRating}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryAndByRatingsHighToLowHandler(@Valid @PathVariable String category, @PathVariable Double maxRating, @PathVariable Double minRating) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndByRatingsHighToLow(category, minRating, maxRating), HttpStatus.OK);
    }

    @GetMapping("/productsByRatingsLowToHigh/{category}/{minRating}/{maxRating}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryAndByRatingsLowToHighHandler(@Valid @PathVariable String category, @PathVariable Double minRating, @PathVariable Double maxRating) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndByRatingsLowToHigh(category, minRating, maxRating), HttpStatus.OK);
    }

    @GetMapping("/productsByMinRating/{category}/{minRating}")
    public ResponseEntity<Set<Product>> getAllProductsByCategoryAndByRatingHandler(@Valid @PathVariable String category, @PathVariable Double minRating) throws ProductException, CustomerException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryAndByRatings(category, minRating), HttpStatus.OK);
    }

    @GetMapping("/getProductStock/{product}")
    public ResponseEntity<Integer> getProductStockNumberHandler(@Valid @PathVariable String product) throws CustomerException, ProductException {
        return new ResponseEntity<>(productService.getStockNumberForProduct(product), HttpStatus.OK);
    }
}
