package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.OrganizationsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/organizations")
public class OrganizationsRestController {

    @Autowired
    private OrganizationsManager manager;

    @PreAuthorize("permitAll")
    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable String organizationId) throws EntityNotFoundException {
        return manager.getInstance(organizationId);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<String> getPage(@PathVariable int page){
        return manager.getPage(page, 10);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/createNew")
    public Organization createOrganization(@RequestBody Organization organization) throws EntityNotFoundException, IdConflictException {
        return manager.create(organization);
    }

    @PreAuthorize("permitAll")
    @DeleteMapping("/{organizationId}")
    public boolean deleteOrganization(@PathVariable String organizationId){
        return manager.delete(organizationId);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/byUser/{userMail}")
    public List<Organization> getByUser(@PathVariable String userMail){
        return manager.findByUser(userMail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/getUsers/{organizationId}")
    public List<User> getUsers(@PathVariable String organizationId) throws EntityNotFoundException {
        return manager.getUsers(organizationId);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/addCollaborator/{organizationId}/{userMail}")
    public void addCollaborator(@PathVariable String organizationId, @PathVariable String userMail, @RequestBody Skill skill) throws EntityNotFoundException {
        manager.addCollaborator(organizationId, userMail, skill);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/addMember/{organizationId}/{userMail}")
    public boolean addMember(@PathVariable String organizationId, @PathVariable String userMail) throws EntityNotFoundException {
        return manager.addMember(organizationId,userMail);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/removeMember/{organizationId}/{userMail}")
    public boolean removeMember(@PathVariable String organizationId, @PathVariable String userMail) throws EntityNotFoundException {
        return manager.removeMember(organizationId,userMail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/findByCreator/{userMail}")
    public List<Organization> findByCreator(@PathVariable String userMail){
        return manager.findByCreator(userMail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/exist/{organizationName}")
    public boolean existsOrganization(@PathVariable String organizationName){
        return manager.exists(organizationName);
    }

    @PreAuthorize("permitAll")
    @PutMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization modifyOrganization(@RequestBody Organization organization) throws EntityNotFoundException {
        return manager.update(organization);
    }

}
