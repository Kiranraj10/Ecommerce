package com.ecommerce.backend.ecommerce_practice.api.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ecommerce.backend.ecommerce_practice.model.DAO.LocalUserDAO;
import com.ecommerce.backend.ecommerce_practice.model.LocalUser;
import com.ecommerce.backend.ecommerce_practice.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private  JWTService jwtService;
    private LocalUserDAO LocalUserDAO;

    public JWTRequestFilter(JWTService jwtService, com.ecommerce.backend.ecommerce_practice.model.DAO.LocalUserDAO localUserDAO) {
        this.jwtService = jwtService;
        LocalUserDAO = localUserDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try{
                String username = jwtService.getUsername(token);
                Optional<LocalUser> opUser= LocalUserDAO.findByUsernameIgnoreCase(username);
                if(opUser.isPresent()){
                    LocalUser user = opUser.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch(JWTDecodeException e){

            }

        }
        filterChain.doFilter(request, response);
    }
}
