package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.RealOrganization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.RealUser;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimpleOrganizationsManagerTest {

    @Autowired
    private OrganizationsManager manager;
    private Organization organization;

    @BeforeEach
    void initOrganization(){
        User user = new RealUser("mail", "luca", 21);
        organization = manager.createNewOrganization(new RealOrganization("org", "description", user.getMail()));
    }

    @AfterEach
    void removeOrganization(){
        boolean result = manager.deleteOrganization("organization1");
        if(!result) fail();
    }

    @Test
    void getOrganizationInstance() {
        assertEquals(organization, manager.getOrganizationInstance(organization.getName()));
    }

    @Test
    void createNewOrganization() {
        assertNotNull(organization);
    }
}