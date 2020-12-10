package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Set;

public interface User {

    boolean checkSkill(Skill skill);

    boolean isEmptySkills();

    boolean checkCompletedProject(Project project);

    boolean isEmptyCompletedProjects();

    void addSkill(Skill skill);

    void addSkills(Set<Skill> skills);

    void removeSkill(Skill skill);

    Set<Skill> getSkills();

    void addCompletedProject(Project project);

    void addCompletedProjects(Set<Project> projects);

    void removeCompletedProject(Project project);

    void addProjectIsProjectManager(Project project);

    void addProjectsIsProjectManager(Set<Project> projects);

    void removeProjectIsProjectManager(Project project);

    void addOrganizationIsOrganizationCreator(Organization organization);

    void addOrganizationsIsOrganizationCreator(Set<Organization> organizations);

    void removeOrganizationIsOrganizationCreator(Organization organization);

    void addOrganization(Organization organization);

    void removeOrganization(Organization organization);

}
