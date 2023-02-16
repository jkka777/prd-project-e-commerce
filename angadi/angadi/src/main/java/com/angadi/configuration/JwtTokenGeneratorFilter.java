package com.angadi.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.*;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("inside generator filter");

        if (authentication != null) {

            System.out.println(authentication);

            /*String encoded = (String) authentication.getCredentials();
            String encodedString = Base64.getEncoder().encodeToString(encoded.getBytes());*/

            SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

            String jwt = Jwts.builder()
                    .setIssuer("admin@angadi")
                    .setSubject("jwt token")
                    .claim("username", authentication.getName())
                    .claim("authorities", getRole(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + 30000000))
                    .signWith(secretKey).compact();

            response.setHeader(SecurityConstants.JWT_HEADER, jwt);

        }
        filterChain.doFilter(request, response);
    }

    private String getRole(Collection<? extends GrantedAuthority> collection) {

        Set<String> authorities = new HashSet<>();

        for (GrantedAuthority ga : collection) {
            authorities.add(ga.getAuthority());
        }

        return String.join(",", authorities);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/logIn");
    }
}
