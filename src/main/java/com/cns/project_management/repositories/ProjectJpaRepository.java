package com.cns.project_management.repositories;

import com.cns.project_management.model.Project;
import com.cns.project_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<Project, Integer> {
    Project findById(int id);
    List<Project> findAllByOwnerName(String name);
    List<Project> findAllByProjectMembersContains(User user);
    @Query(value = "select * from project where end_date_time between :start and :end and status = :status ;", nativeQuery = true)
    List<Project> findAllByEndDateTimeBetweenAndStatus(@Param("start") String start, @RequestParam("end") String end, @RequestParam("status") int status);
    long countDistinctByStatus(int status);
}
