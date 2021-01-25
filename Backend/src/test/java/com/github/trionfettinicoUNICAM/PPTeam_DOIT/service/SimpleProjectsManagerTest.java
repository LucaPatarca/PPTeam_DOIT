package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SimpleProjectsManagerTest {

    @Autowired
    private ProjectsManager manager;
    @Autowired
    private OrganizationsManager organizationsManager;

    private Project project;

    @BeforeEach
    void initManager() throws EntityNotFoundException {
        User user = new User("mail", "luca");
        Organization organization = organizationsManager.createNewOrganization(new Organization("org", "description", user.getMail()));
        project = manager.createNewProject(new Project(organization.getId(), user.getMail(), "test", "description"));
    }

    @AfterEach
    void removeProjects(){
        manager.deleteProject(project.getId());
        organizationsManager.deleteOrganization(project.getOrganizationId());
    }

    @Test
    void createNewProject() {
        assertNotNull(project);
    }

    @Test
    void getProjectInstance() throws EntityNotFoundException {
        assertEquals(project, manager.getProjectInstance(project.getId()));
    }

    @Test
    void closeProject() {
    }

    @Test
    void deleteProject() {
    }

    @Test
    void updateProject() {
    }

    @Test
    void getPage() {
    }

    @Test
    void exists() {
    }

    @Test
    void existsSignature() {
    }

    @Test
    void findByOrganizationId() {
    }
}