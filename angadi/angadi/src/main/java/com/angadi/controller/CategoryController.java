package com.angadi.controller;

import com.angadi.exception.CategoryException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Category;
import com.angadi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addCategory/{email}")
    public ResponseEntity<Category> saveCategoryHandler(@RequestBody Category category, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(categoryService.addCategory(category, email), HttpStatus.CREATED);
    }

    @PutMapping("/updateCategory/{email}")
    public ResponseEntity<Category> updateCategoryHandler(@RequestBody Category category, @PathVariable String email) throws CustomerException, CategoryException {
        return new ResponseEntity<>(categoryService.updateCategory(category, email), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCategory/{email}")
    public ResponseEntity<Category> deleteCategoryHandler(@RequestBody Category category, @PathVariable String email) throws CustomerException, CategoryException {
        return new ResponseEntity<>(categoryService.deleteCategory(category, email), HttpStatus.CREATED);
    }

    @GetMapping("/getAllCategory/{email}")
    public ResponseEntity<List<Category>> getAllCategoryHandler() {
        return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.CREATED);
    }

}
