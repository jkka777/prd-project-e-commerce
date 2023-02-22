package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellerId;

    @NotBlank(message = "Company name cannot be blank")
    @NotEmpty(message = "Company name cannot be empty")
    @NotNull(message = "Company name cannot be null")
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*${4,55}", message = "Company name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String sellerName;

    @NotBlank(message = "Mobile cannot be blank")
    @NotEmpty(message = "Mobile cannot be empty")
    @NotNull(message = "Mobile cannot be null")
    @Pattern(regexp = "^[6-9][0-9]{9}", message = "Enter valid 10 digit mobile number")
    private String phone;

    @Email
    @NotBlank(message = "Email cannot be blank")
    @NotEmpty(message = "Email cannot be empty")
    @NotNull(message = "Email cannot be null")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Street cannot be blank")
    @NotEmpty(message = "Street cannot be empty")
    @NotNull(message = "Street cannot be null")
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*${4,55}", message = "Street name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String street;

    @NotBlank(message = "City cannot be blank")
    @NotEmpty(message = "City cannot be empty")
    @NotNull(message = "City cannot be null")
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*${4,55}", message = "City name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String city;

    @NotBlank(message = "Pin-code cannot be blank")
    @NotEmpty(message = "Pin-code cannot be empty")
    @NotNull(message = "Pin-code cannot be null")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Pin-code  can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String pinCode;

    @NotBlank(message = "State cannot be blank")
    @NotEmpty(message = "State cannot be empty")
    @NotNull(message = "State cannot be null")
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*${4,55}", message = "State name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String state;

    @NotBlank(message = "Country cannot be blank")
    @NotEmpty(message = "Country cannot be empty")
    @NotNull(message = "Country cannot be null")
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*${4,55}", message = "Country name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String country;

    @JsonIgnore
    @OneToOne
    @PrimaryKeyJoinColumn
    private OrderItem orderItem;

}
