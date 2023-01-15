package com.cns.project_management.service;

import com.cns.project_management.model.Project;
import com.cns.project_management.model.User;
import com.cns.project_management.repositories.ProjectJpaRepository;
import com.cns.project_management.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
@Scope(scopeName = "prototype")
public class ProjectOperationImpl implements ProjectOperations{
    @Autowired
    private ProjectJpaRepository projectJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public String createProject(Project project, String userName) {
        String status = "";
        try {
            User user = userJpaRepository.findByName(userName);
            project.setStatus(0);
            project.setOwner(user);
            projectJpaRepository.save(project);
            status = "success";
        } catch (Exception e) {
            status = "failed";
        }
        return status;
    }

    @Override
    public String updateProject(Project project) {
        String status = "";
        try {
            projectJpaRepository.save(project);
            status = "success";
        } catch (Exception e) {
            status = "failed";
        }
        return status;
    }

    @Override
    public String deleteProject(int id) {
        return null;
    }

    @Override
    public String addEmployee(String employeeName, int projectId) {
        String status = "";
        try {
            User user =  userJpaRepository.findByName(employeeName);
            Project project = projectJpaRepository.findById(projectId);
            if(project.getProjectMembers().size() < 5){
                project.getProjectMembers().add(user);
                projectJpaRepository.save(project);
                status = "success";
            }
            else{
                status = "over";
            }
        } catch (Exception e) {
            status = "failed";
        }
        return status;
    }

    @Override
    public String removeEmployee(String employeeName, int projectId) {
        String status = "";
        try {
            User user =  userJpaRepository.findByName(employeeName);
            Project project = projectJpaRepository.findById(projectId);
            project.getProjectMembers().remove(user);
            projectJpaRepository.save(project);
            status = "success";
        } catch (Exception e) {
            status = "failed";
        }
        return status;
    }

    @Override
    public String changeProjectStatus(int value, int projectId) {
        String status = "";
        try {
            Project project = projectJpaRepository.findById(projectId);
            project.setStatus(value);
            status = "success";
        } catch (Exception e) {
            status = "failed";
        }
        return status;
    }

    @Override
    public List<Project> totalProjects() {
        return projectJpaRepository.findAll();
    }

    @Override
    public List<Project> ownerProjects(String userName) {
        return projectJpaRepository.findAllByOwnerName(userName);
    }

    @Override
    public List<Project> employeeProjects(String userName) {
        return null;
    }
}
