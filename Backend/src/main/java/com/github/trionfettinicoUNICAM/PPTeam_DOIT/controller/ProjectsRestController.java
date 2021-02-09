package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
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
public class ProjectsRestController implements ProjectsController {

    @Autowired
    private ProjectsManager manager;

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/{projectID}")
    public Project getInstance(@PathVariable String projectID) throws EntityNotFoundException { return manager.getInstance(projectID); }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project create(@RequestBody Project project) throws EntityNotFoundException, IdConflictException { return manager.create(project); }

    @Override
    @PreAuthorize("permitAll")
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project update(@RequestBody Project project) throws EntityNotFoundException { return manager.update(project); }

    @Override
    @PreAuthorize("permitAll")
    @DeleteMapping(value = "/{projectID}")
    public boolean delete(@PathVariable String projectID) throws EntityNotFoundException { return manager.delete(projectID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/exist/{projectID}")
    public boolean exists(@PathVariable String projectID) { return manager.exists(projectID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<String> getPage(@PathVariable int page) { return manager.getPage(page, 10); }

    @Override
    @PreAuthorize("permitAll")
    @PutMapping(value = "/close/{projectID}")
    public boolean closeProject(@PathVariable String projectID) throws EntityNotFoundException {
        return manager.closeProject(projectID);
    }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping("/addNeededSkill/{projectId}")
    public boolean addNeededSkill(@PathVariable String projectId, @RequestBody String skillName) throws EntityNotFoundException{
        return manager.addNeededSkill(projectId, skillName);
    }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping("/removeNeededSkill/{projectId}")
    public boolean removeNeededSkill(@PathVariable String projectId, @RequestBody String skillName) throws EntityNotFoundException{
        return manager.removeNeededSkill(projectId, skillName);
    }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping("/submit/{projectId}/{userMail}")
    public boolean submit(@PathVariable String projectId, @PathVariable String userMail, @RequestBody Role role) throws EntityNotFoundException{
        return manager.submit(projectId, userMail, role);
    }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping("/acceptCandidate/{projectId}")
    public boolean acceptCandidate(@PathVariable String projectId,@RequestBody Role userRole) throws EntityNotFoundException{
        return manager.acceptCandidate(projectId, userRole);
    }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping("/rejectCandidate/{projectId}")
    public boolean rejectCandidate(@PathVariable String projectId, @RequestBody Role userRole) throws EntityNotFoundException{
        return manager.rejectCandidate(projectId, userRole);
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/getSubmissions/{userMail}")
    public List<Role> getUserSubmissions(@PathVariable String userMail) throws EntityNotFoundException{
        return manager.getUserSubmissions(userMail);
    }
}
