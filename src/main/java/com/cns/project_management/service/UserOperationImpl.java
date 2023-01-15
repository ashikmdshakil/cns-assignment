package com.cns.project_management.service;

import com.cns.project_management.model.Role;
import com.cns.project_management.model.User;
import com.cns.project_management.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(scopeName = "prototype")
public class UserOperationImpl implements UserOperations{
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private Role role;

    @Override
    public String registerUser(User user) {
        String status = "";
        try {
            //encrypting user password
            user.setPassword(encoder.encode(user.getPassword()));
            role.setId(1);
            role.setName("user");
            user.setRole(role);
            userJpaRepository.save(user);
            status ="success";
        } catch (Exception e) {
            e.printStackTrace();
            status = "failed";
        }
        return status;
    }

    @Override
    public User getUserByName(String name) {
       return  userJpaRepository.findByName(name);
    }

    @Override
    public String updateUser(User user) {
        String status = "";
        //condition is to check whether password is changed or not
        if(user.getPassword().toCharArray()[0] == '$' && user.getPassword().toCharArray()[1] == '2' && user.getPassword().toCharArray()[2] == 'a' && user.getPassword().toCharArray().length > 20){
            userJpaRepository.save(user);
            status = "success";
        }
        else{
            user.setPassword(encoder.encode(user.getPassword()));
            userJpaRepository.save(user);
            status = "success";
        }
        return status;
    }

    @Override
    public String logoutUser() {
        String status = "";
        try {
            SecurityContextHolder.clearContext();
            status="success";
        } catch (Exception e) {
            status="failed";
        }
        return status;
    }

    @Override
    public List<User> getUsers() {
        return userJpaRepository.findAll();
    }

}
