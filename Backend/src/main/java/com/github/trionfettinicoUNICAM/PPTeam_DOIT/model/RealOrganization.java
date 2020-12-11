package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.ProjectsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RealOrganization implements Organization{

    @Autowired
    private ProjectsManager projectsManager;
    @Id
    private String name;
    private final Set<String> membersMails;

    public RealOrganization(String name){
        this.name = name;
        this.membersMails = new HashSet<>();
    }

    @Override
    public Project createProject(String name, String description, User creator) {
        return projectsManager.openNewEmptyProject(this, name, description, creator);
    }

    @Override
    public void deleteProject(String projectName) {

    }

    @Override
    public void closeProject(String projectName) {

    }

    @Override
    public Map<Role, Project> getProjectsCandidates() {
        return null;
    }

    @Override
    public void addExpert(User user, Role role) {

    }

    @Override
    public void removeExpert(User expert) {

    }

    @Override
    public void addMember(User user) {
        membersMails.add(user.getMail());
    }

    @Override
    public void removeMember(User user) {

    }

    @Override
    public Set<Project> getOpenProjects() {
        return null;
    }

    @Override
    public Set<Project> getCloseProjects() {
        return null;
    }

    @Override
    public Set<Project> getProjects() {
        return null;
    }

    @Override
    public Set<String> getMembersMails() {
        return membersMails;
    }

    @Override
    public Set<String> getExpertsMails() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getCreatorMail() {
        return null;
    }

    @Override
    public void setName(String name) {
        this.name  = name;
    }

    @Override
    public void setDescription(String description) {

    }
}
