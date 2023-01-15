package com.cns.project_management.repositories;

import com.cns.project_management.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<Project, Integer> {
    Project findById(int id);
    List<Project> findAllByOwnerName(String name);
}
