package com.cns.project_management.repositories;

import com.cns.project_management.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepository extends JpaRepository<Project, Integer> {
    Project findById(int id);
}
