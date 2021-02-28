package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.BasicOrganizationInformation;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.OrganizationsManager;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.security.PermissionComponent;
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
    @Autowired
    private PermissionComponent permissionComponent;

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/{organizationID}")
    public Organization getInstance(@PathVariable String organizationID) throws EntityNotFoundException {
        return manager.getInstance( organizationID);
    }

    @Override
    @PreAuthorize("@permissionComponent.sameMail(authentication, #organization.creatorMail)")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization create(@RequestBody Organization organization) throws EntityNotFoundException, IdConflictException { return manager.create(organization); }

    @Override
    @PreAuthorize("@permissionComponent.isFounder(authentication, #organization.id)")
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization update(@RequestBody Organization organization) throws EntityNotFoundException, IdConflictException { return manager.update(organization); }

    @Override
    @PreAuthorize("@permissionComponent.isFounder(authentication, #organizationID)")
    @DeleteMapping(value = "/{organizationID}")
    public boolean delete(@PathVariable String organizationID) { return manager.delete(organizationID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/exist/{organizationID}")
    public boolean exists(@PathVariable String organizationID) { return manager.exists(organizationID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<BasicOrganizationInformation> getPage(@PathVariable int page) throws EntityNotFoundException {
        return manager.getPage(page, 10);
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/byUser/{userMail}")
    public List<Organization> getByUser(@PathVariable("userMail") String userMail){
        return manager.findByUser(userMail);
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/getUsers/{organizationId}")
    public List<UserEntity> getUsers(@PathVariable String organizationId) throws EntityNotFoundException {
        return manager.getUsers(organizationId);
    }

    @Override
    @PreAuthorize("@permissionComponent.isFounder(authentication, #organizationId)")
    @PostMapping("/addCollaborator/{organizationId}/{userMail}")
    public void addCollaborator(@PathVariable String organizationId,@PathVariable String userMail, @RequestBody Skill skill) throws EntityNotFoundException {
        manager.addCollaborator(organizationId, userMail, skill);
    }

    @Override
    @PreAuthorize("@permissionComponent.isFounder(authentication, #organizationId)")
    @PostMapping("/addMember/{organizationId}/{userMail}")
    public boolean addMember(@PathVariable String organizationId, @PathVariable String userMail) throws EntityNotFoundException {
        return manager.addMember(organizationId,userMail);
    }

    @Override
    @PreAuthorize("@permissionComponent.isFounder(authentication, #organizationId) or @permissionComponent.sameMail(authentication, #userMail)")
    @PostMapping("/removeMember/{organizationId}/{userMail}/{removeProjects}")
    public boolean removeMember(@PathVariable String organizationId, @PathVariable String userMail, @PathVariable Boolean removeProjects) throws EntityNotFoundException {
        return manager.removeMember(organizationId,userMail, removeProjects);
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/findByCreator/{userMail}")
    public List<Organization> findByCreator(@PathVariable String userMail){
        return manager.findByCreator(userMail);
    }

}
