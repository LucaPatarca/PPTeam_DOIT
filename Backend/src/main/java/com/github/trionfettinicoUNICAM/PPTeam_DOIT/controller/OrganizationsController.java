package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.BasicOrganizationInformation;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.OrganizationsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public Organization getOrganization(@PathVariable String organizationId) throws EntityNotFoundException {
        return organizationsManager.getOrganizationInstance(organizationId);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<BasicOrganizationInformation> getPage(@PathVariable int page){
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
    public List<User> getUsers(@PathVariable String organizationId) throws EntityNotFoundException {
        return organizationsManager.getUsers(organizationId);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/addCollaborator/{organizationId}/{userMail}")
    public void addCollaborator(@PathVariable String organizationId, @PathVariable String userMail, @RequestBody Skill skill) throws EntityNotFoundException {
        organizationsManager.addCollaborator(organizationId, userMail, skill);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/addMember/{organizationId}/{userMail}")
    public ResponseEntity<Object> addMember(@PathVariable String organizationId, @PathVariable String userMail){
        // TODO: 21/01/2021 implementare
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/removeMember/{organizationId}/{userMail}")
    public ResponseEntity<Object> removeMember(@PathVariable String organizationId, @PathVariable String userMail){
        // TODO: 21/01/2021 implementare
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/listCreatorOrg/{userMail}")
    public List<Organization> listOfOrganizationOfUser(@PathVariable String userMail){
        return organizationsManager.findByCreator(userMail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/exist/{organizationName}")
    public boolean existsOrganization(@PathVariable String organizationName){
        return organizationsManager.exists(organizationName);
    }

    @PreAuthorize("permitAll")
    @PutMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization modifyOrganization(@RequestBody Organization organization) throws EntityNotFoundException {
        return organizationsManager.updateOrganization(organization);
    }

}
