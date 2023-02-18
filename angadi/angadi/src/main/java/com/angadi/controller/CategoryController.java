package com.angadi.controller;

import com.angadi.exception.CategoryException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Category;
import com.angadi.service.CategoryService;
import jakarta.validation.Valid;
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

    @PostMapping("/addCategory")
    public ResponseEntity<Category> saveCategoryHandler(@Valid @RequestBody Category category) throws CustomerException {
        return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<Category> updateCategoryHandler(@Valid @RequestBody Category category) throws CustomerException, CategoryException {
        return new ResponseEntity<>(categoryService.updateCategory(category), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<Category> deleteCategoryHandler(@Valid @RequestBody Category category) throws CustomerException, CategoryException {
        return new ResponseEntity<>(categoryService.deleteCategory(category), HttpStatus.OK);
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<List<Category>> getAllCategoryHandler() {
        return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
    }

}
