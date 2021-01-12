package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a group of {@link User}s working together for a project. Each {@link User} is represented inside the
 * team by a {@link Role}
 * @see Role
 */
public class Team{

    // TODO: 15/12/20 togliere projectName dato che e' ridondante

    // TODO: 09/01/21 trasformare in un set di ruoli (nel progetto) ed eliminare questa classe dalla faccia della terra

    private Set<Role> roles;
    private String projectName;

    public Team(Set<Role> roles, String projectName) throws IllegalArgumentException {
        setProjectName(projectName);
        addRoles(roles);
    }

    public Team() {
        this.projectName = "";
        this.roles = new HashSet<>();
    }

    public Team(String projectName) throws IllegalArgumentException {
        setProjectName(projectName);
        this.roles = new HashSet<>();
    }

    /**
     * Adds a new {@link User} to this team by means of a {@link Role}.
     * @param role the new role to add
     * @return true if the role is added successfully, false instead
     */
    public boolean addRole(Role role) {

        return roles.add(Objects.requireNonNull(role, "Role is null"));
    }

    /**
     * Adds a collection of {@link Role}s to this team.
     * @param roles the roles to add
     * @return true if all the {@link Role}s are added successfully, false instead
     */
    public boolean addRoles(Collection<Role> roles) {
        Objects.requireNonNull(roles, "Roles is null");
        for (Role r: roles) Objects.requireNonNull(r, "Roles contains a null role");
        return this.roles.addAll(roles);
    }

    /**
     * Removes a new {@link Role} from this team.
     * @param role the new role to remove
     * @return true if the role is removed successfully, false instead
     */
    public boolean removeRole(Role role) {

        return roles.remove(Objects.requireNonNull(role, "Role is null"));
    }

    /**
     * @return the number of member ({@link Role}s) in this team.
     */
    public int size() {
        return roles.size();
    }

    /**
     * @return the project's name which this team is working for
     */
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) throws IllegalArgumentException {
        if(projectName.length() == 0) throw new IllegalArgumentException("ProjectName is empty");
        this.projectName = projectName;
    }

    public void setRoles(Set<Role> roles) {
        Objects.requireNonNull(roles, "Roles is null");
        for (Role r: roles) Objects.requireNonNull(r, "Roles contains a null role");
        this.roles = roles;
    }

    /**
     * @return all the members ({@link Role}s) in this team.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team realTeam = (Team) o;
        return roles.equals(realTeam.roles) && projectName.equals(realTeam.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roles, projectName);
    }
}
