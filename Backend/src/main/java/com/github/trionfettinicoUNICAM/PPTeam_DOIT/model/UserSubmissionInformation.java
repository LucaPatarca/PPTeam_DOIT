package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.List;

public class UserSubmissionInformation {
    private String organizationId;
    private String organizationName;
    private String projectId;
    private String projectName;
    private List<Role> role;

    public UserSubmissionInformation(){

    }

    public UserSubmissionInformation(Organization organization, Project project, List<Role> role){
        this.organizationId=organization.getId();
        this.organizationName=organization.getName();
        this.projectId=project.getId();
        this.projectName=project.getTitle();
        this.role=role;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
}
