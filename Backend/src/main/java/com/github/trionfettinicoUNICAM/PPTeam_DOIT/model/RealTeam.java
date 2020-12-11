package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RealTeam implements Team{

    private Set<Role> roles;
    private String projectName;

    public RealTeam(String projectName) throws IllegalArgumentException {
        setProjectName(projectName);
        this.roles=new HashSet<>();
    }

    @Override
    public boolean addRole(Role role) {
        return roles.add(role);
    }

    @Override
    public boolean addRoles(Collection<Role> roles) {
        return this.roles.addAll(roles);
    }

    @Override
    public boolean removeRole(Role role) {
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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
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
