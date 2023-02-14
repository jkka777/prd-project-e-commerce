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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

            String jwt = Jwts.builder()
                    .setIssuer("admin@angadi")
                    .setSubject("jwt token")
                    .claim("username", authentication.getName())
                    .claim("authorities", getRole(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + 3600))
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
