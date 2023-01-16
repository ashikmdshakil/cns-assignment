package com.cns.project_management.repositories;

import com.cns.project_management.model.Role;
import com.cns.project_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    User findById(int id);
    boolean existsByName(String name);

}
