package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a group of {@link User}s working together for a project. Each {@link User} is represented inside the
 * team by a {@link Role}
 * @see Role
 */
public interface Team {
    // TODO: 15/12/20 far diventare una classe come fatto con project
    /**
     * Adds a new {@link User} to this team by means of a {@link Role}.
     * @param role the new role to add
     * @return true if the role is added successfully, false instead
     */
    boolean addRole(RealRole role);

    /**
     * Adds a collection of {@link Role}s to this team.
     * @param roles the roles to add
     * @return true if all the {@link Role}s are added successfully, false instead
     */
    boolean addRoles(Collection<RealRole> roles);

    /**
     * Removes a new {@link Role} from this team.
     * @param role the new role to remove
     * @return true if the role is removed successfully, false instead
     */
    boolean removeRole(RealRole role);

    /**
     * @return the number of member ({@link Role}s) in this team.
     */
    int size();

    /**
     * @return all the members ({@link Role}s) in this team.
     */
    Set<RealRole> getRoles();

    void setRoles(Set<RealRole> roles);

    /**
     * @return the project's name which this team is working for
     */
    String getProjectName();

    void setProjectName(String projectName);

}
