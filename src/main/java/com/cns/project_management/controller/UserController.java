package com.cns.project_management.controller;

import com.cns.project_management.model.User;
import com.cns.project_management.security.AuthenticationService;
import com.cns.project_management.service.UserOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserOperations userOperations;

    //registration
    @PostMapping(value = "public/registerUser", consumes = "application/json")
    public String registerUser(@RequestBody User user) {
        return userOperations.registerUser(user);
    }

    //login
    @PostMapping("public/authenticateUser")
    public HashMap<String, String> authenticateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return authenticationService.authenticateUser(request);
    }

    //fetching profile
    @GetMapping("private/user/profile")
    public User fetchProfile(Principal principal) throws IOException {
        return userOperations.getUserByName(principal.getName());
    }

    //updating profile
    @PostMapping(value = "private/user/profile/update", consumes = "application/json")
    public String updateProfile(@RequestBody User user) {
        return userOperations.updateUser(user);
    }

    //logout
    @PostMapping("private/user/profile/logout")
    public String logout() throws IOException {
        return userOperations.logoutUser();
    }

    //user list
    @GetMapping("private/user/list")
    public List<User> userList() throws IOException {
        return userOperations.getUsers();
    }
}
