package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Expert;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;

import java.util.Set;

public interface OrganizationManager {

    Organization getOrganizationIstance(int organizationId);

    Expert addExpert(Organization organization, Expert expert);

    Set<Expert> addExperts(Organization organization, Set<Expert> experts);

    Expert removeExpert(Organization organization, Expert expert);

    Project addProject(Organization organization, Project project);

    Set<Project> addProjects(Organization organization, Set<Project> projects);

    Project removeProject(Organization organization, Project project);

    Project closeProject(Organization organization, Project project);

    User addUser(Organization organization, User user);

    Set<User> addUsers(Organization organization, Set<User> users);

    User removeUser(Organization organization, User user);

    boolean isEmptyExperts();

    boolean isEmptyProjects();

    boolean isEmptyUsers();

    void modifyProjectsManager(Organization organization, ProjectsManager projectsManager);

    void setName(Organization organization, String name);

    void setDescription(Organization organization, String description);

    void checkOrganization(Organization organization);

}
