package com.cns.project_management.service;

import com.cns.project_management.model.Project;

import java.util.List;

public interface ProjectOperations {
    String createProject(Project project);
    String updateProject(Project project);
    String deleteProject(int id);
    String addEmployee(String employeeName, int projectId);
    String removeEmployee(String employeeName, int projectId);
    String changeProjectStatus(int status, int projectId);
    List<Project> totalProjects();
    List<Project> ownerProjects();
    List<Project> employeeProjects();
}
