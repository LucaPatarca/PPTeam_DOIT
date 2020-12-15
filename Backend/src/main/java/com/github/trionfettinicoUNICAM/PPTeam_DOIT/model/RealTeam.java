package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RealTeam implements Team{

    private Set<RealRole> roles;
    private String projectName;

    public RealTeam(Set<RealRole> roles, String projectName) throws IllegalArgumentException {
        setProjectName(projectName);
        this.roles=roles;
    }

    public RealTeam() {
        this.projectName = "";
        this.roles = new HashSet<>();
    }

    public RealTeam(String projectName) {
        this.projectName = projectName;
        this.roles = new HashSet<>();
    }

    @Override
    public boolean addRole(RealRole role) {
        return roles.add(role);
    }

    @Override
    public boolean addRoles(Collection<RealRole> roles) {
        return this.roles.addAll(roles);
    }

    @Override
    public boolean removeRole(RealRole role) {
        return roles.remove(role);
    }

    @Override
    public int size() {
        return roles.size();
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public void setProjectName(String projectName) throws IllegalArgumentException {
        if(projectName.length() == 0) throw new IllegalArgumentException("ProjectName is empty");
        this.projectName = projectName;
    }

    public void setRoles(Set<RealRole> roles) {
        this.roles = roles;
    }

    public Set<RealRole> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealTeam realTeam = (RealTeam) o;
        return roles.equals(realTeam.roles) && projectName.equals(realTeam.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roles, projectName);
    }
}
