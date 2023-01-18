package com.cns.project_management.repositories;

import com.cns.project_management.model.Project;
import com.cns.project_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<Project, Integer> {
    Project findById(int id);
    List<Project> findAllByOwnerName(String name);
    List<Project> findAllByProjectMembersContains(User user);
    List<Project> findAllByEndDateTimeIsGreaterThanAndEndDateTimeIsLessThanAndStatus(LocalDateTime start, LocalDateTime end, int status);
    long countDistinctByStatus(int status);
}
