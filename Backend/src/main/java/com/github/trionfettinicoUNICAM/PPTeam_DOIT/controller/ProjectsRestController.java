package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
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
public class ProjectsRestController {

    @Autowired
    private ProjectsManager manager;


    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<String> getPage(@PathVariable Integer page){
        return manager.getPage(page, 10);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/{projectID}")
    public Project getProject(@PathVariable String projectID) throws EntityNotFoundException {
        return manager.getInstance(projectID);
    }

    @PreAuthorize("permitAll")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project createNewProject(@RequestBody Project project) throws EntityNotFoundException {
        return manager.create(project);
    }

    @PreAuthorize("permitAll")
    @DeleteMapping(value = "/{projectID}")
    public boolean deleteProject(@PathVariable String projectID){
        return manager.delete(projectID);
    }

    @PreAuthorize("permitAll")
    @PutMapping(value = "/close/{projectID}")
    public boolean closeProject(@PathVariable String projectID) throws EntityNotFoundException {
        return manager.closeProject(projectID);
    }


    @PreAuthorize("permitAll")
    @PutMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project modifyProject(@RequestBody Project project) throws EntityNotFoundException {
        return manager.update(project);
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
    @PostMapping("/rejectCandidate/{projectId}")
    public boolean rejectCandidate(@PathVariable String projectId, @RequestBody Role userRole){
        // TODO: 21/01/2021 implementare
        return false;
    }

    @PreAuthorize("permitAll")
    @GetMapping("/exist/{projectSignature}")
    public boolean existsProject(@PathVariable String projectSignature){
        return manager.exists(projectSignature);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/getSubmissions/{userMail}")
    public List<Role> getUserSubmissions(@PathVariable String userMail){
        // TODO: 21/01/2021 implementare
        return null;
    }

}
