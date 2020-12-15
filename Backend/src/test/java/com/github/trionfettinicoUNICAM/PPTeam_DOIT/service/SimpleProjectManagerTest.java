package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimpleProjectManagerTest {

    @Autowired
    private ProjectsManager manager;
    @Autowired
    private OrganizationsManager organizationsManager;
    private Project project;

    @BeforeEach
    void initManager(){
        User user = new User("mail", "luca", 21);
        Organization organization = organizationsManager.createNewOrganization(new Organization("org", "description", user.getMail()));
        project = manager.createNewProject(new Project(organization.getName(), user.getMail(), "test", "description"));
    }

    @AfterEach
    void removeProjects(){
        manager.deleteProject(project.getID());
        organizationsManager.deleteOrganization("org");
    }

    @Test
    void openNewEmptyProject() {
        assertNotNull(project);
    }

    @Test
    void getProjectInstance() {
        assertEquals(project, manager.getProjectInstance(project.getID()));
    }
}