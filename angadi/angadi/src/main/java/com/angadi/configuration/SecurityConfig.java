package com.angadi.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
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
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/hello").permitAll()
                .requestMatchers(HttpMethod.POST, "/customer/addCustomer").permitAll()
                .requestMatchers(HttpMethod.POST, "/customer/addCustomerAddress").authenticated()
                .requestMatchers(HttpMethod.PUT, "/customer/updateCustomer").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/customer/deleteCustomer").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/customer/getCustomerDetails").hasAnyRole("USER", "SUPPLIER")
                .requestMatchers(HttpMethod.GET, "/customer/getAllCustomerDetails").hasRole("ADMIN")
                .requestMatchers("/address/**",
                        "/orders/**",
                        "/orderDetails/**",
                        "/payments/**",
                        "/product/**",
                        "/shipping/**",
                        "/supplier/**",
                        "/wallet/**",
                        "/walletTransaction/**").authenticated()
                .anyRequest().authenticated()
                .and()
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
