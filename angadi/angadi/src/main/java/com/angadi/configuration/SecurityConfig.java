package com.angadi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityConfiguration(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/customers")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
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
