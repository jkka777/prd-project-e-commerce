package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;

    @NotBlank(message = "Category name cannot be blank")
    @NotEmpty(message = "Category name cannot be empty")
    @NotNull(message = "Category name cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}", message = "Category name should contain minimum of 4 and maximum of 15 characters " +
            "and can contain a-z or A-Z or 0-9")
    private String categoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

}
