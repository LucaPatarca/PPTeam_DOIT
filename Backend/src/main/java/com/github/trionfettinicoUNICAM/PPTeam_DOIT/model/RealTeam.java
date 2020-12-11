package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RealTeam implements Team{

    private final Set<Role> roles;
    private final String projectName;

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
    public Set<Role> getTeamRoles() {
        return roles;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public void setProjectName(String projectName throws IllegalArgumentException {
        if(projectName.length() == 0) throw new IllegalArgumentException("ProjectName is empty");
        this.projectName = projectName;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        RealTeam realTeam = (RealTeam) object;
        return projectName.equals(realTeam.projectName);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), projectName);
    }
}
