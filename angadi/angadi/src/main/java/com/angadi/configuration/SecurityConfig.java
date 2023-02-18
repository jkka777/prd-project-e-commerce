package com.angadi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityConfiguration(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .cors().configurationSource(request -> {

                    CorsConfiguration cfg = new CorsConfiguration();

                    cfg.setAllowedOrigins(Collections.singletonList("*"));
                    cfg.setAllowedMethods(Collections.singletonList("*"));
                    cfg.setAllowCredentials(true);
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    cfg.setExposedHeaders(List.of("Authorization"));
                    cfg.setMaxAge(3600L);
                    return cfg;
                })
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/customer/hello", "/customer/welcome").permitAll()
                .requestMatchers("/customer/addCustomer").permitAll()
                .requestMatchers(
                        "/customer/updateCustomer",
                        "/address/addAddress",
                        "/address/updateAddress",
                        "/address/deleteAddress/*",
                        "/address/getUserAddressList").authenticated()
                .requestMatchers("/customer/getCustomerDetails").hasAnyRole("USER", "SELLER")
                .requestMatchers(
                        "/address/getAllAddressList",
                        "/customer/deleteCustomer",
                        "/customer/getAllCustomerDetails",
                        "/orders/getAllOrders").hasRole("ADMIN")
                .requestMatchers(
                        "/category/**",
                        "/product/addProduct/**",
                        "/product/updateProduct",
                        "/product/deleteProduct",
                        "/seller/**",
                        "/shipping").hasAnyRole("ADMIN", "SELLER")
                .requestMatchers(
                        "/cart/**",
                        "/cartItem/**",
                        "/orders/saveOrder",
                        "/orders/updateOrder",
                        "/orders/cancelOrder",
                        "/orders/getOrder/**",
                        "/orders/getOrdersOfCustomer",
                        "/orderItems/**",
                        "/payments/**").hasRole("USER")
                .requestMatchers(
                        "/product/allProductsByCategory/*",
                        "/product/productsByPriceHighToLow/**",
                        "/product/productsByPriceLowToHigh/**",
                        "/product/productsByRatingsHighToLow/**",
                        "/product/productsByRatingsLowToHigh/**",
                        "/product/productsByMinRating/**",
                        "/product/getProductStock/*",
                        "/wallet/**",
                        "/walletTransaction/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .formLogin()
                .and()
                .httpBasic();

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
