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
public class OrganizationsRestController implements OrganizationsController {

    @Autowired
    private OrganizationsManager manager;

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/{organizationID}")
    public Organization getInstance(@PathVariable String organizationID) throws EntityNotFoundException { return manager.getInstance(organizationID); }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization create(@RequestBody Organization organization) throws EntityNotFoundException, IdConflictException { return manager.create(organization); }

    @Override
    @PreAuthorize("permitAll")
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization update(@RequestBody Organization organization) throws EntityNotFoundException { return manager.update(organization); }

    @Override
    @PreAuthorize("permitAll")
    @DeleteMapping(value = "/{organizationID}")
    public boolean delete(@PathVariable String organizationID) throws EntityNotFoundException { return manager.delete(organizationID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/exist/{organizationID}")
    public boolean exists(@PathVariable String organizationID) { return manager.exists(organizationID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<String> getPage(@PathVariable int page){
        return manager.getPage(page, 10);
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/byUser/{userMail}")
    public List<Organization> getByUser(@PathVariable String userMail){
        return manager.findByUser(userMail);
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/getUsers/{organizationId}")
    public List<User> getUsers(@PathVariable String organizationId) throws EntityNotFoundException {
        return manager.getUsers(organizationId);
    }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping("/addCollaborator/{organizationId}/{userMail}")
    public void addCollaborator(@PathVariable String organizationId,@PathVariable String userMail, @RequestBody Skill skill) throws EntityNotFoundException {
        manager.addCollaborator(organizationId, userMail, skill);
    }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping("/addMember/{organizationId}/{userMail}")
    public boolean addMember(@PathVariable String organizationId, @PathVariable String userMail) throws EntityNotFoundException {
        return manager.addMember(organizationId,userMail);
    }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping("/removeMember/{organizationId}/{userMail}")
    public boolean removeMember(@PathVariable String organizationId, @PathVariable String userMail) throws EntityNotFoundException {
        return manager.removeMember(organizationId,userMail);
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/findByCreator/{userMail}")
    public List<Organization> findByCreator(@PathVariable String userMail){
        return manager.findByCreator(userMail);
    }

}
