package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
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
        User user = new User("mail", "luca");
        organization = manager.createNewOrganization(new Organization("org", "description", user.getMail()));
    }

    @AfterEach
    void removeOrganization(){
        boolean result = manager.deleteOrganization("organization1");
        assertTrue(result);
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