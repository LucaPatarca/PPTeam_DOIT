package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.ProjectsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/projects")
public class ProjectsController {

    // TODO: 15/12/20 ritornare dai metodi una Response (o qualcosa del genere) per segnalare lo stato del ritorno

    @Autowired
    private ProjectsManager projectsManager;


    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<Project> getPage(@PathVariable Integer page){
        return projectsManager.getPage(page, 10);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/{projectID}")
    public Project getProject(@PathVariable String projectID){
        return projectsManager.getProjectInstance(projectID);
    }

    @PreAuthorize("permitAll")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project createNewProject(@RequestBody Project project){
        project.setID(project.getOrganizationName()+"."+project.getName());
        return projectsManager.createNewProject(project);
    }

    @PreAuthorize("permitAll")
    @DeleteMapping(value = "/delete/{projectID}")
    public boolean deleteProject(@PathVariable String projectID){
        return projectsManager.deleteProject(projectID);
    }

    @PreAuthorize("permitAll")
    @PutMapping(value = "/close/{projectID}")
    public boolean closeProject(@PathVariable String projectID){
        return projectsManager.closeProject(projectID);
    }


    @PreAuthorize("permitAll")
    @PutMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean modifyProject(@RequestBody Project project){
        project.setID(project.getOrganizationName()+"."+project.getName());
        return projectsManager.updateProject(project);
    }

}
