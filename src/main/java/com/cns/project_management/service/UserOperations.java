package com.cns.project_management.service;

import com.cns.project_management.model.User;

import java.util.List;

public interface UserOperations {
    String registerUser(User user);
    User getUserByName(String name);
    String updateUser(User user);
    String logoutUser();
    List<User> getUsers();

}
