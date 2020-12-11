package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RealTeam implements Team{

    private final Set<Role> roles;
    private final String projectName;

    public RealTeam(String projectName) {
        this.projectName = projectName;
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
}
