package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.ProjectsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/project")
public class ProjectsController {

    @Autowired
    private ProjectsManager projectsManager;

    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<Project> getPage(@PathVariable Integer page){
        return projectsManager.getPage(page, 10);
    }
}
