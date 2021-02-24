package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SimpleProjectsManagerTest {

    /*@Autowired
    private ProjectsManager manager;
    @Autowired
    private OrganizationsManager organizationsManager;

    private Project project;

    @BeforeEach
    void initManager() throws EntityNotFoundException, IdConflictException {
        User user = new User("mail", "luca");
        Organization organization = organizationsManager.create(new Organization("org", "description", user.getMail()));
        project = manager.create(new Project(organization.getId(), user.getMail(), "test", "description"));
    }

    @AfterEach
    void removeProjects(){
        manager.delete(project.getId());
        organizationsManager.delete(project.getOrganizationId());
    }

    @Test
    void createNewProject() {
        assertNotNull(project);
    }

    @Test
    void getProjectInstance() throws EntityNotFoundException {
        assertEquals(project, manager.getInstance(project.getId()));
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
    }*/
}