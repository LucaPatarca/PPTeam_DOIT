package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.OrganizationsManager;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/organizations")
public class OrganizationsController {

    @Autowired
    private OrganizationsManager organizationsManager;

    @PreAuthorize("permitAll")
    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable String organizationId){
        return organizationsManager.getOrganizationInstance(organizationId);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<Organization> getPage(@PathVariable int page){
        return organizationsManager.getPage(page, 10);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/createNew")
    public Organization createOrganization(@RequestBody Organization organization){
        return organizationsManager.createNewOrganization(organization);
    }

    @PreAuthorize("permitAll")
    @DeleteMapping("/{organizationId}")
    public boolean deleteOrganization(@PathVariable String organizationId){
        return organizationsManager.deleteOrganization(organizationId);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/byUser/{userMail}")
    public List<Organization> getByUser(@PathVariable String userMail){
        return organizationsManager.findByUser(userMail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/getUsers/{organizationId}")
    public List<User> getUsers(@PathVariable String organizationId){
        return organizationsManager.getUsers(organizationId);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/addCollaborator/{organizationId}/{userMail}")
    public boolean addCollaborator(@PathVariable String organizationId, @PathVariable String userMail, @RequestBody Skill skill){
        return organizationsManager.addCollaborator(organizationId, userMail, skill);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/listCreatorOrg/{userMail}")
    public List<Organization> listOfOrganizationOfUser(@PathVariable String userMail){
        return organizationsManager.findCreator(userMail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/exist/{organizationName}")
    public boolean existsUser(@PathVariable String organizationName){
        return organizationsManager.exists(organizationName);
    }

    @PreAuthorize("permitAll")
    @PutMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization modifyOrganization(@RequestBody Organization organization){
        return organizationsManager.updateOrganization(organization);
    }

}
