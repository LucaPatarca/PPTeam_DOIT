package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.BasicProjectInformation;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Role;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.ProjectsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/projects")
public class ProjectsController {

    // TODO: 15/12/20 ritornare dai metodi una Response (o qualcosa del genere) per segnalare lo stato del ritorno

    @Autowired
    private ProjectsManager projectsManager;


    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<BasicProjectInformation> getPage(@PathVariable Integer page){
        return projectsManager.getPage(page, 10);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/{projectID}")
    public Project getProject(@PathVariable String projectID) throws EntityNotFoundException {
        return projectsManager.getProjectInstance(projectID);
    }

    @PreAuthorize("permitAll")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project createNewProject(@RequestBody Project project) throws EntityNotFoundException {
        return projectsManager.createNewProject(project);
    }

    @PreAuthorize("permitAll")
    @DeleteMapping(value = "/{projectID}")
    public boolean deleteProject(@PathVariable String projectID){
        return projectsManager.deleteProject(projectID);
    }

    @PreAuthorize("permitAll")
    @PutMapping(value = "/close/{projectID}")
    public boolean closeProject(@PathVariable String projectID) throws EntityNotFoundException {
        return projectsManager.closeProject(projectID);
    }


    @PreAuthorize("permitAll")
    @PutMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project modifyProject(@RequestBody Project project) throws EntityNotFoundException {
        return projectsManager.updateProject(project);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/addNeededSkill/{projectId}")
    public boolean addNeededSkill(@PathVariable String projectId, @RequestBody String skillName){
        // TODO: 21/01/2021 implementare
        return false;
    }

    @PreAuthorize("permitAll")
    @PostMapping("/removeNeededSkill/{projectId}")
    public boolean removeNeededSkill(@PathVariable String projectId, @RequestBody String skillName){
        // TODO: 21/01/2021 implementare
        return false;
    }

    @PreAuthorize("permitAll")
    @PostMapping("/submit/{projectId}/{userMail}")
    public boolean submit(@PathVariable String projectId, @PathVariable String userMail, @RequestBody Role role){
        // TODO: 21/01/2021 implementare
        return false;
    }

    @PreAuthorize("permitAll")
    @PostMapping("/acceptCandidate/{projectId}")
    public boolean acceptCandidate(@PathVariable String projectId, @RequestBody Role userRole){
        // TODO: 21/01/2021 implementare
        return false;
    }

    @PreAuthorize("permitAll")
    @GetMapping("/exist/{projectSignature}")
    public boolean existsProject(@PathVariable String projectSignature){
        return projectsManager.exists(projectSignature);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/getSubmissions/{userMail}")
    public List<Role> getUserSubmissions(@PathVariable String userMail){
        // TODO: 21/01/2021 implementare
        return null;
    }

}
