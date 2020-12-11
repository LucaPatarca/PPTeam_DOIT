package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimpleProjectManagerTest {

    @Autowired
    private ProjectsManager manager;
    private Project project;

    @BeforeEach
    void initManager(){
        Organization organization = new RealOrganization("org");
        User user = new RealUser("mail");
        organization.addMember(user);
        project = manager.openNewEmptyProject(organization,"title", "description", user);
    }

    @AfterEach
    void removeProjects(){
        manager.deleteProject(project);
    }

    @Test
    void getProjectInstance() {
        assertNotNull(project);
        assertEquals(project, manager.getProjectInstance("title"));
    }

    @Test
    void openNewEmptyProject() {

    }
}