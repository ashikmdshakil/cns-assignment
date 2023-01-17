package com.cns.project_management.controller;

import com.cns.project_management.model.Project;
import com.cns.project_management.service.ProjectOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@CrossOrigin(origins= {"/**"}, maxAge = 4800, allowCredentials = "false" )
@RestController
public class ManagementController {
    @Autowired
    private ProjectOperations projectOperations;

    //creating a new project
    @PostMapping(value = "private/project/create", consumes = "application/json")
    public String createProject(@RequestBody Project project, Principal principal) {
        return projectOperations.createProject(project, principal.getName());
    }

    //updating project
    @PostMapping(value = "private/project/update", consumes = "application/json")
    public String updateProject(@RequestBody Project project){
        return projectOperations.updateProject(project);
    }

    //deleting project
    @PostMapping(value = "private/project/delete")
    public String deleteProject(@RequestParam("id") int projectId) {
        return projectOperations.deleteProject(projectId);
    }

    //add employee to project
    @PostMapping(value = "private/project/employee/add")
    public String addEmployee(@RequestParam("name") String employeeName, @RequestParam("projectId") int projectId) {
        return projectOperations.addEmployee(employeeName, projectId);
    }

    //remove employee to project
    @PostMapping(value = "private/project/employee/delete")
    public String deleteEmployee(@RequestParam("name") String employeeName, @RequestParam("projectId") int projectId) {
        return projectOperations.removeEmployee(employeeName, projectId);
    }

    //update project status
    @PostMapping(value = "private/project/status/update")
    public String updateStatus(@RequestParam("status") int status, @RequestParam("projectId") int projectId) {
        return projectOperations.changeProjectStatus(status, projectId);
    }

    //fetch all projects
    @GetMapping(value = "private/project/all")
    public List<Project> fetchAllProjects() {
        return projectOperations.totalProjects();
    }

    //fetch all projects of owner
    @GetMapping(value = "private/project/owner")
    public List<Project> fetchAllProjectsOfOwner(Principal principal) {
        return projectOperations.ownerProjects(principal.getName());
    }

    //fetch all projects of employee
    @GetMapping(value = "private/project/employee")
    public List<Project> fetchAllProjectsOfEmployee(Principal principal) {
        return projectOperations.employeeProjects(principal.getName());
    }

    //fetch all projects by certain date and status
    @GetMapping(value = "private/project/search")
    public List<Project> fetchAllProjectsByDate(@RequestParam("startingDate") String startTime, @RequestParam("endTime") String endTime, @RequestParam("status") int status) throws ParseException {
        return projectOperations.searchByDate(startTime, endTime, status);
    }


    //fetch overview
    @GetMapping(value = "private/project/overview")
    public HashMap<String, String> fetchAllProjectsOverView() throws ParseException {
        return projectOperations.projectReports();
    }

}
