package com.cns.project_management.security;

import com.cns.project_management.model.Role;
import com.cns.project_management.model.User;
import com.cns.project_management.repositories.UserJpaRepository;
import com.cns.project_management.security.ApplicationUserDetails;
import com.cns.project_management.service.UserOperations;
import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.HashMap;

@Service
@Scope(scopeName = "prototype")
public class AuthenticationService {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private JWTTokenUtils jwtTokenUtils;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private User user;
    @Autowired
    private UserOperations userOperations;


    public HashMap<String, String> authenticateUser(HttpServletRequest request) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        String token = "";
        String userName = "";
        String password = "";
        String upd = request.getHeader("Authorization");
        String pair = new String(Base64.decodeBase64(upd.substring(6)));
        userName = pair.split(":")[0];
        password = pair.split(":")[1];
        user = userJpaRepository.findByName(userName);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            String authToken = jwtTokenUtils.generateToken(new ApplicationUserDetails(user), "user");
            response.put("status","success");
            response.put("token",authToken);
            response.put("id", String.valueOf(user.getId()));
        } else {
            response.put("status","failed");
        }
        return response;
    }


    public UserDetails getValidUserDetails(HttpServletRequest request) {
        UserDetails userDetails = null;
        JWTTokenUtils jwtTokenUtils1 = new JWTTokenUtils();
        try {
            String token = request.getHeader("Authorization");
            String jwtToken = token.substring(7);
            String userName = jwtTokenUtils1.getUsernameFromToken(jwtToken);
            //String roleName = jwtTokenUtils1.getRoleFromToken(jwtToken);
            User user = userJpaRepository.findByName(userName);
            userDetails = new ApplicationUserDetails(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }

    //To logout an user
    public void logoutUser(HttpSession httpSession, Principal principal, String role, String token) {
        SecurityContextHolder.clearContext();
    }

}
