package com.cns.project_management.repositories;

import com.cns.project_management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<Role, Integer> {
}
