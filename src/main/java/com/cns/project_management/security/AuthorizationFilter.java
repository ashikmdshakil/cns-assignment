package com.cns.project_management.security;

import com.cns.project_management.model.Role;
import com.cns.project_management.model.User;
import com.cns.project_management.repositories.UserJpaRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private User user = new User();
    private UserJpaRepository userJpaRepository;
    private Role role = new Role();
    private AuthenticationService utils= new AuthenticationService();

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserJpaRepository userJpaRepository, AuthenticationService utils) {
        super(authenticationManager);
        this.userJpaRepository = userJpaRepository;
        this.utils = utils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request.getServletPath().equals("/public/authenticateUser")){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            utils.authenticateUser(request,response);
        }
        else if(request.getServletPath().startsWith("/public/")) {
            chain.doFilter(request, response);
        }
        else{
            UsernamePasswordAuthenticationToken authentication;
            authentication = null;
            UserDetails principal = utils.getValidUserDetails(request);
            if(principal != null){
                authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request,response);
            }
            else{
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
