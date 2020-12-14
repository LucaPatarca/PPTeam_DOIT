package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.OrganizationsManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/organizations")
public class OrganizationsController {

    @Autowired
    private OrganizationsManager organizationsManager;

    @PreAuthorize("permitAll")
    @GetMapping("/{organizationName}")
    public Organization getOrganization(@PathVariable String organizationName){
        return organizationsManager.getOrganizationInstance(organizationName);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<Organization> getPage(@PathVariable int page){
        return organizationsManager.getPage(page, 10);
    }

}
