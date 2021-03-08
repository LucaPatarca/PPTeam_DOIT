package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
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
public class ProjectsRestController implements ProjectsController {

    @Autowired
    private ProjectsManager manager;

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/{projectID}")
    public Project getInstance(@PathVariable String projectID) throws EntityNotFoundException { return manager.getInstance(projectID); }

    @Override
    @PreAuthorize("@permissionComponent.sameMail(authentication, #project.creatorMail)")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project create(@RequestBody Project project) throws EntityNotFoundException, IdConflictException {
        return manager.create(project);
    }

    @Override
    @PreAuthorize("@permissionComponent.isProjectManager(authentication, #project.id)")
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project update(@RequestBody Project project) throws EntityNotFoundException, IdConflictException { return manager.update(project); }

    @Override
    @PreAuthorize("@permissionComponent.isProjectManager(authentication, #projectID)")
    @DeleteMapping(value = "/{projectID}")
    public boolean delete(@PathVariable String projectID) { return manager.delete(projectID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/exist/{projectID}")
    public boolean exists(@PathVariable String projectID) { return manager.exists(projectID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<BasicProjectInformation> getPage(@PathVariable int page) throws EntityNotFoundException { return manager.getPage(page, 10); }

    @Override
    @PreAuthorize("@permissionComponent.isProjectManager(authentication, #projectID)")
    @PutMapping(value = "/close/{projectID}")
    public boolean closeProject(@PathVariable String projectID) throws EntityNotFoundException {
        return manager.closeProject(projectID);
    }

    @Override
    @PreAuthorize("@permissionComponent.sameMail(authentication, #role.userMail)")
    @PostMapping("/submit/{projectId}")
    public boolean submit(@PathVariable String projectId, @RequestBody Role role) throws EntityNotFoundException{
        return manager.submit(projectId, role);
    }

    @Override
    @PreAuthorize("@permissionComponent.isTeamManager(authentication, #projectId, #userRole.skill)")
    @PostMapping("/acceptCandidate/{projectId}")
    public boolean acceptCandidate(@PathVariable String projectId,@RequestBody Role userRole) throws EntityNotFoundException{
        return manager.acceptCandidate(projectId, userRole);
    }

    @Override
    @PreAuthorize("@permissionComponent.isTeamManager(authentication, #projectId, #userRole.skill) " +
            "or @permissionComponent.sameMail(authentication, #userRole.userMail)")
    @PostMapping("/rejectCandidate/{projectId}")
    public boolean rejectCandidate(@PathVariable String projectId, @RequestBody Role userRole) throws EntityNotFoundException{
        return manager.rejectCandidate(projectId, userRole);
    }

    @Override
    @PreAuthorize("permitAll()")
    @GetMapping("/getSubmissions/{userMail}")
    public List<Role> getUserSubmissions(@PathVariable String userMail) throws EntityNotFoundException{
        return manager.getUserSubmissions(userMail);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/removeTeamMember/{projectId}")
    public boolean removeTeamMember(@PathVariable String projectId, @RequestBody Role userRole) throws EntityNotFoundException{
        return manager.removeTeamMember(projectId, userRole);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/byUser/{userMail}")
    public List<Project> getByUser(@PathVariable String userMail) throws EntityNotFoundException{
        return manager.getByUser(userMail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/getProjectsOfOrganization/{organizationID}")
    public List<Project> getProjectPageOfOrganization(@PathVariable String organizationID ) { return manager.findByOrganizationId(organizationID); }
}
