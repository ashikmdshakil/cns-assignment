package com.cns.project_management.security;

import com.cns.project_management.model.User;
import com.cns.project_management.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Scope(scopeName = "prototype")
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthenticationService authenticationService = new AuthenticationService();
        final String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                Authentication authentication = null;
                UserDetails principal = getValidUserDetails(request, response);
                if (principal != null) {
                    authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request, response);
    }

    public UserDetails getValidUserDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDetails userDetails = null;
        JWTTokenUtils jwtTokenUtils1 = new JWTTokenUtils();
        try {
            String token = request.getHeader("Authorization");
            String jwtToken = token.substring(7);
            String userName = jwtTokenUtils1.getUsernameFromToken(jwtToken);
            User user = userJpaRepository.findByName(userName);
            userDetails = new ApplicationUserDetails(user);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return userDetails;
    }
}
